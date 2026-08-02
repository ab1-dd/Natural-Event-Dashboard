package use_case.addToFavourite;

import entity.FavouriteList;
import entity.NaturalEvent;

public class AddToFavouriteInteractor implements AddToFavouriteInputBoundary {
    private final FavouriteList favouriteList;

    public AddToFavouriteInteractor(FavouriteList favouriteList) {
        this.favouriteList = favouriteList;
    }

    public boolean addFavourite(NaturalEvent naturalEvent) {
        if (favouriteList.addNaturalEvent(naturalEvent)) {
            System.out.println("Add to favourite successfully!");
            return true;
        }else{
            System.out.println("The event has been added to favourite.");
            return false;
        }
    }
}
