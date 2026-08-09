package use_case.addToFavourite;

import entity.FavouriteList;
import entity.NaturalEvent;

public class AddToFavouriteInteractor implements AddToFavouriteInputBoundary {
    private final FavouriteList favouriteList;
    private final AddToFavouriteOutputBoundary presenter;

    public AddToFavouriteInteractor(FavouriteList favouriteList, AddToFavouriteOutputBoundary presenter) {
        this.favouriteList = favouriteList;
        this.presenter = presenter;
    }

    @Override
    public void execute(AddToFavouriteInputData inputData) {
        String eventID = inputData.getEventId();
        boolean duplicated = favouriteList.contain(eventID);
        if (duplicated) {
            AddToFavouriteOutputData outputData= new AddToFavouriteOutputData(false);
            presenter.present(outputData);
        }else{
            favouriteList.addNaturalEvent(eventID);
            AddToFavouriteOutputData outputData= new AddToFavouriteOutputData(true);
            presenter.present(outputData);

        }
    }
}
