package use_case.generateChart;

import java.util.List;

public class GenerateChartOutputData {
    private final List<String> binLabels; // X axis label
    private final int[] counts;          // Y axis frequency
    private final int maxCount;          // max frequency
    private final boolean useCaseFailed;

    public GenerateChartOutputData(List<String> binLabels, int[] counts, int maxCount, boolean useCaseFailed) {
        this.binLabels = binLabels;
        this.counts = counts;
        this.maxCount = maxCount;
        this.useCaseFailed = useCaseFailed;
    }

    public List<String> getBinLabels() {
        return binLabels;
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