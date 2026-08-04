package interface_adapter.generateChart;


import java.util.ArrayList;
import java.util.List;

public class ChartState {
    private List<String> binLabels = new ArrayList<>();
    private int[] counts = new int[0];
    private int maxCount = 0;
    private String errorMessage = null;

    public ChartState(ChartState copy) {
        if (copy != null) {
            this.binLabels = new ArrayList<>(copy.binLabels);
            this.counts = copy.counts != null ? copy.counts.clone() : new int[0];
            this.maxCount = copy.maxCount;
            this.errorMessage = copy.errorMessage;
        }
    }

    public ChartState() {}

    public List<String> getBinLabels() {
        return binLabels;
    }

    public void setBinLabels(List<String> binLabels) {
        this.binLabels = binLabels;
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