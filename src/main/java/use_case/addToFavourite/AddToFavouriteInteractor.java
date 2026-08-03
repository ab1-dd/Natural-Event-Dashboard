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
    public void addFavourite(NaturalEvent naturalEvent) {
        boolean isAdded = favouriteList.addNaturalEvent(naturalEvent);
        if (isAdded) {
            presenter.prepareSuccessView(naturalEvent.getTitle());
        }else{
            presenter.prepareFailView(naturalEvent.getTitle(), "This event is ALREADY in your favourite list!");
        }
    }
}
