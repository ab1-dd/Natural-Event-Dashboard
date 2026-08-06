package entity;

import java.time.LocalDate;
import java.util.Map;

/**
 * A time-series analytics report: how many natural events fell into each
 * calendar bucket (day, week, or month) of a date range.
 */
public class TimeSeriesReport {

    /**
     * How events are grouped along the time axis.
     */
    public enum Granularity {
        DAY,
        WEEK,
        MONTH
    }

    private final Granularity granularity;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Map<LocalDate, Integer> eventFrequencyByDate;

    /**
     * @param granularity            how events are grouped along the time axis
     * @param startDate              start of the window
     * @param endDate                end of the window
     * @param eventFrequencyByDate   ordered map of bucket-start-date to event count,
     *                               covering every bucket in the window
     */
    public TimeSeriesReport(Granularity granularity, LocalDate startDate, LocalDate endDate,
                             Map<LocalDate, Integer> eventFrequencyByDate) {
        this.granularity = granularity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventFrequencyByDate = eventFrequencyByDate;
    }

    public Granularity getGranularity() {
        return granularity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Map<LocalDate, Integer> getEventFrequencyByDate() {
        return eventFrequencyByDate;
    }
}
