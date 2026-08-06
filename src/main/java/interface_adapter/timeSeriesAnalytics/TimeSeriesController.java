package interface_adapter.timeSeriesAnalytics;

import entity.NaturalEvent;
import entity.TimeSeriesReport.Granularity;
import use_case.timeSeriesAnalytics.TimeSeriesAnalyticsInputBoundary;
import use_case.timeSeriesAnalytics.TimeSeriesAnalyticsInputData;

import java.util.List;

public class TimeSeriesController {

    private final TimeSeriesAnalyticsInputBoundary interactor;

    public TimeSeriesController(TimeSeriesAnalyticsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void generate(List<NaturalEvent> events, int windowDays, Granularity granularity) {
        TimeSeriesAnalyticsInputData inputData = new TimeSeriesAnalyticsInputData(events, windowDays, granularity);
        interactor.execute(inputData);
    }
}
