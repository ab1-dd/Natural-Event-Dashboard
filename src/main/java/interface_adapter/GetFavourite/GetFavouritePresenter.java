package interface_adapter.GetFavourite;

import use_case.GetFavourite.GetFavouriteOutputBoundary;
import use_case.GetFavourite.GetFavouriteOutputData;

public class GetFavouritePresenter implements GetFavouriteOutputBoundary {
    private final GetFavouriteViewModel viewModel;

    public GetFavouritePresenter(
            GetFavouriteViewModel viewModel) {

        this.viewModel = viewModel;
    }

    @Override
    public void present(GetFavouriteOutputData outputData) {

        viewModel.setFavouriteEventIds(
                outputData.getFavouriteEventIds()
        );
    }

    public GetFavouriteViewModel getViewModel() {
        return viewModel;
    }
}
