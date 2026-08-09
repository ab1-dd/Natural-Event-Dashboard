package interface_adapter.addToFavourite;

import use_case.addToFavourite.AddToFavouriteOutputBoundary;
import use_case.addToFavourite.AddToFavouriteOutputData;


public class AddToFavouritePresenter implements AddToFavouriteOutputBoundary {
    private AddToFavouriteViewModel viewModel;

    public void present(AddToFavouriteOutputData outputData){
        if (outputData.isSuccess()){
            viewModel = new AddToFavouriteViewModel("Successfully added to favourites!");
        }else {
            viewModel = new AddToFavouriteViewModel("Failed added to favourites because duplication!");
        }
    }
    public AddToFavouriteViewModel getViewModel() {
        return viewModel;
    }
}