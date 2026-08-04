package use_case.generateChart;

public interface GenerateChartOutputBoundary {
    void prepareSuccessView(GenerateChartOutputData outputData);
    void prepareFailView(String error);
}