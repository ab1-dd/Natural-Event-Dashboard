package interface_adapter.generateChart;


import use_case.generateChart.GenerateChartOutputBoundary;
import use_case.generateChart.GenerateChartOutputData;

public class GenerateChartPresenter implements GenerateChartOutputBoundary {

    private final ChartViewModel viewModel;

    public GenerateChartPresenter(ChartViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(GenerateChartOutputData outputData) {
        ChartState state = new ChartState(viewModel.getState());
        state.setBinLabels(outputData.getBinLabels());
        state.setCounts(outputData.getCounts());
        state.setMaxCount(outputData.getMaxCount());
        state.setErrorMessage(null);

        viewModel.setState(state);
    }

    @Override
    public void prepareFailView(String error) {
        ChartState state = new ChartState(viewModel.getState());
        state.setErrorMessage(error);

        viewModel.setState(state);
    }
}