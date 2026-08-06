package interface_adapter.timeSeriesAnalytics;

import use_case.timeSeriesAnalytics.TimeSeriesAnalyticsOutputBoundary;
import use_case.timeSeriesAnalytics.TimeSeriesAnalyticsOutputData;

public class TimeSeriesPresenter implements TimeSeriesAnalyticsOutputBoundary {

    private final TimeSeriesViewModel viewModel;

    public TimeSeriesPresenter(TimeSeriesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(TimeSeriesAnalyticsOutputData outputData) {
        TimeSeriesState state = new TimeSeriesState(viewModel.getState());
        state.setPeriodLabels(outputData.getPeriodLabels());
        state.setCounts(outputData.getCounts());
        state.setMaxCount(outputData.getMaxCount());
        state.setErrorMessage(null);

        viewModel.setState(state);
    }

    @Override
    public void prepareFailView(String error) {
        TimeSeriesState state = new TimeSeriesState(viewModel.getState());
        state.setErrorMessage(error);

        viewModel.setState(state);
    }
}
