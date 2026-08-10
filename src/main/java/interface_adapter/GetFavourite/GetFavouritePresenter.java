package interface_adapter.GetFavourite;

import use_case.getFavourite.GetFavouriteOutputBoundary;
import use_case.getFavourite.GetFavouriteOutputData;

public class GetFavouritePresenter implements GetFavouriteOutputBoundary {
    private final GetFavouriteViewModel viewModel;

    public GetFavouritePresenter(
            GetFavouriteViewModel viewModel) {

        this.viewModel = viewModel;
    }

    @Override
    public void present(GetFavouriteOutputData outputData) {

        viewModel.setFavouriteEventInfoList(
                outputData.getFavouriteEventInfo()
        );
    }

    public GetFavouriteViewModel getViewModel() {
        return viewModel;
    }
}
