package interface_adapter.timeSeriesAnalytics;

import java.util.ArrayList;
import java.util.List;

public class TimeSeriesState {

    private List<String> periodLabels = new ArrayList<>();
    private int[] counts = new int[0];
    private int maxCount = 0;
    private String errorMessage = null;

    public TimeSeriesState() {
    }

    public TimeSeriesState(TimeSeriesState copy) {
        if (copy != null) {
            this.periodLabels = new ArrayList<>(copy.periodLabels);
            this.counts = copy.counts != null ? copy.counts.clone() : new int[0];
            this.maxCount = copy.maxCount;
            this.errorMessage = copy.errorMessage;
        }
    }

    public List<String> getPeriodLabels() {
        return periodLabels;
    }

    public void setPeriodLabels(List<String> periodLabels) {
        this.periodLabels = periodLabels;
    }

    public int[] getCounts() {
        return counts;
    }

    public void setCounts(int[] counts) {
        this.counts = counts;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean hasError() {
        return errorMessage != null && !errorMessage.isBlank();
    }
}
