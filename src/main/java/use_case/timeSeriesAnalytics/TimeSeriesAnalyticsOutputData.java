package use_case.timeSeriesAnalytics;

import java.util.List;

public class TimeSeriesAnalyticsOutputData {

    private final List<String> periodLabels; // X-axis labels, one per bucket (e.g. week-start dates)
    private final int[] counts;               // Y-axis event counts, aligned with periodLabels
    private final int maxCount;                // highest count, so the view can scale the chart
    private final boolean useCaseFailed;

    public TimeSeriesAnalyticsOutputData(List<String> periodLabels, int[] counts, int maxCount, boolean useCaseFailed) {
        this.periodLabels = periodLabels;
        this.counts = counts;
        this.maxCount = maxCount;
        this.useCaseFailed = useCaseFailed;
    }

    public List<String> getPeriodLabels() {
        return periodLabels;
    }

    public int[] getCounts() {
        return counts;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
