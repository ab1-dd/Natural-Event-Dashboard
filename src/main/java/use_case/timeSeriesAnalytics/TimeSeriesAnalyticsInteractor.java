package use_case.timeSeriesAnalytics;

import entity.NaturalEvent;
import entity.TimeSeriesReport.Granularity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds a time-series (events-per-bucket) breakdown of the currently
 * retrieved events, for the "Sara" user story: she picks a time window
 * (e.g. past 90 days) and a granularity (day/week/month) and sees how many
 * events fell into each bucket.
 *
 * Buckets are calendar-aligned (weeks start on Monday, months start on the
 * 1st) and every bucket in the window is included -- even ones with zero
 * events -- so the resulting line chart reads as a continuous series.
 */
public class TimeSeriesAnalyticsInteractor implements TimeSeriesAnalyticsInputBoundary {

    private static final DateTimeFormatter DAY_LABEL_FMT = DateTimeFormatter.ofPattern("yy-MM-dd");
    private static final DateTimeFormatter MONTH_LABEL_FMT = DateTimeFormatter.ofPattern("yyyy-MM");

    private final TimeSeriesAnalyticsOutputBoundary presenter;

    public TimeSeriesAnalyticsInteractor(TimeSeriesAnalyticsOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(TimeSeriesAnalyticsInputData inputData) {
        List<NaturalEvent> events = inputData.getEvents();
        int windowDays = inputData.getWindowDays();
        Granularity granularity = inputData.getGranularity();

        if (events == null || events.isEmpty()) {
            presenter.prepareFailView("No event data available to plot chart.");
            return;
        }
        if (windowDays <= 0) {
            presenter.prepareFailView("Time window must be greater than 0 days.");
            return;
        }
        if (granularity == null) {
            presenter.prepareFailView("A granularity (day, week, or month) must be selected.");
            return;
        }

        LocalDate today = LocalDate.now();
        LocalDate windowStart = today.minusDays(windowDays);

        List<LocalDate> datesInWindow = parseDatesInWindow(events, windowStart, today);

        if (datesInWindow.isEmpty()) {
            presenter.prepareFailView("No events found in the selected " + windowDays + "-day window.");
            return;
        }

        Map<LocalDate, Integer> buckets = buildEmptyBuckets(windowStart, today, granularity);
        for (LocalDate date : datesInWindow) {
            LocalDate bucketKey = bucketStart(date, granularity);
            buckets.merge(bucketKey, 1, Integer::sum);
        }

        List<String> labels = new ArrayList<>();
        int[] counts = new int[buckets.size()];
        int maxCount = 0;
        int i = 0;
        for (Map.Entry<LocalDate, Integer> entry : buckets.entrySet()) {
            labels.add(formatLabel(entry.getKey(), granularity));
            counts[i] = entry.getValue();
            maxCount = Math.max(maxCount, counts[i]);
            i++;
        }

        presenter.prepareSuccessView(new TimeSeriesAnalyticsOutputData(labels, counts, maxCount, false));
    }

    private List<LocalDate> parseDatesInWindow(List<NaturalEvent> events, LocalDate windowStart, LocalDate windowEnd) {
        List<LocalDate> dates = new ArrayList<>();
        for (NaturalEvent event : events) {
            try {
                String rawDate = event.getEventDate();
                if (rawDate != null && rawDate.length() >= 10) {
                    LocalDate date = LocalDate.parse(rawDate.substring(0, 10));
                    if (!date.isBefore(windowStart) && !date.isAfter(windowEnd)) {
                        dates.add(date);
                    }
                }
            } catch (Exception ignoreIt) {
                // Skip events with an unparsable date rather than failing the whole chart.
            }
        }
        return dates;
    }

    private Map<LocalDate, Integer> buildEmptyBuckets(LocalDate windowStart, LocalDate windowEnd, Granularity granularity) {
        Map<LocalDate, Integer> buckets = new LinkedHashMap<>();
        LocalDate cursor = bucketStart(windowStart, granularity);
        LocalDate lastBucket = bucketStart(windowEnd, granularity);
        while (!cursor.isAfter(lastBucket)) {
            buckets.put(cursor, 0);
            cursor = nextBucket(cursor, granularity);
        }
        return buckets;
    }

    private LocalDate bucketStart(LocalDate date, Granularity granularity) {
        switch (granularity) {
            case WEEK:
                return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            case MONTH:
                return date.withDayOfMonth(1);
            case DAY:
            default:
                return date;
        }
    }

    private LocalDate nextBucket(LocalDate bucketStart, Granularity granularity) {
        switch (granularity) {
            case WEEK:
                return bucketStart.plusWeeks(1);
            case MONTH:
                return bucketStart.plusMonths(1);
            case DAY:
            default:
                return bucketStart.plusDays(1);
        }
    }

    private String formatLabel(LocalDate bucketStart, Granularity granularity) {
        if (granularity == Granularity.MONTH) {
            return YearMonth.from(bucketStart).format(MONTH_LABEL_FMT);
        }
        return bucketStart.format(DAY_LABEL_FMT);
    }
}
