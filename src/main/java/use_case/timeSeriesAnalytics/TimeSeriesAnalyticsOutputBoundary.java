package use_case.timeSeriesAnalytics;

public interface TimeSeriesAnalyticsOutputBoundary {
    void prepareSuccessView(TimeSeriesAnalyticsOutputData outputData);
    void prepareFailView(String error);
}
