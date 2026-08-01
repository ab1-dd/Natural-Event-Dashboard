package use_case.addToFavourite;

import entity.FavouriteList;
import entity.NaturalEvent;

public class AddToFavouriteInteractor implements AddToFavouriteInputBoundary {
    private final FavouriteList favouriteList;

    public AddToFavouriteInteractor(FavouriteList favouriteList) {
        this.favouriteList = favouriteList;
    }

    public void addFavourite(NaturalEvent naturalEvent){
        favouriteList.addNaturalEvent(naturalEvent);
        System.out.println("Add to favourite successfully!");
    }
}
