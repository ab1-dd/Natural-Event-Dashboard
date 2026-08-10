package interface_adapter.addToFavourite;

import use_case.addToFavourite.AddToFavouriteOutputBoundary;
import use_case.addToFavourite.AddToFavouriteOutputData;


public class AddToFavouritePresenter implements AddToFavouriteOutputBoundary {
    private AddToFavouriteViewModel viewModel;

    public AddToFavouritePresenter(AddToFavouriteViewModel viewModel){
        this.viewModel = viewModel;
    }

    public void present(AddToFavouriteOutputData outputData){
        if (outputData.isSuccess()){
            this.viewModel.setMessage("Successfully added to favourites!");
        }else {
            this.viewModel.setMessage("Failed added to favourites because duplication!");
        }
    }
    public AddToFavouriteViewModel getViewModel() {
        return viewModel;
    }
}