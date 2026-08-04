package use_case.generateChart;

import java.util.List;

public class GenerateChartOutputData {
    private final List<String> binLabels; // X 轴显示的日期区间标签 (如 "08-01 ~ 08-05")
    private final int[] counts;          // Y 轴对应的频次数组
    private final int maxCount;          // 最大频次 (方便 View 计算柱状图高度)
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