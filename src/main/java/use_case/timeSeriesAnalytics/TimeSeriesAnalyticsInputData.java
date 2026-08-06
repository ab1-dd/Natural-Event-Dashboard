package use_case.timeSeriesAnalytics;

import entity.NaturalEvent;
import entity.TimeSeriesReport.Granularity;

import java.util.List;

public class TimeSeriesAnalyticsInputData {

    private final List<NaturalEvent> events;
    private final int windowDays;
    private final Granularity granularity;

    /**
     * @param events      the currently retrieved NASA EONET events to analyze
     * @param windowDays  the analytics time window, e.g. 30, 90, or 365 days back from today
     * @param granularity how to bucket events along the time axis: by day, week, or month
     */
    public TimeSeriesAnalyticsInputData(List<NaturalEvent> events, int windowDays, Granularity granularity) {
        this.events = events;
        this.windowDays = windowDays;
        this.granularity = granularity;
    }

    public List<NaturalEvent> getEvents() {
        return events;
    }

    public int getWindowDays() {
        return windowDays;
    }

    public Granularity getGranularity() {
        return granularity;
    }
}
