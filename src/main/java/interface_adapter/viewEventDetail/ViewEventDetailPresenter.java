package interface_adapter.viewEventDetail;

import use_case.viewEventDetail.ViewEventDetailOutputBoundary;
import use_case.viewEventDetail.ViewEventDetailOutputData;

public class ViewEventDetailPresenter implements ViewEventDetailOutputBoundary {

    private final EventDetailViewModel viewModel;

    public ViewEventDetailPresenter(EventDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewEventDetailOutputData outputData) {
        EventDetailState state = new EventDetailState(viewModel.getState());
        state.setTitle(outputData.getTitle());
        state.setCategory(outputData.getCategory());
        state.setStatus(outputData.getStatus());
        state.setEventDate(outputData.getEventDate());
        state.setCoordinates(outputData.getCoordinates());
        state.setSourceLink(outputData.getSourceLink());
        state.setErrorMessage(null);

        viewModel.setState(state);
    }

    @Override
    public void prepareFailView(String error) {
        EventDetailState state = new EventDetailState(viewModel.getState());
        state.setErrorMessage(error);

        viewModel.setState(state);
    }
}
