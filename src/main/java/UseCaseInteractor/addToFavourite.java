package UseCaseInteractor;

import InputBoundry.addToFavouriteInterface;
import entity.FavouriteList;
import entity.NaturalEvent;

public class addToFavourite implements addToFavouriteInterface {
    private final FavouriteList favouriteList;

    public addToFavourite(FavouriteList favouriteList) {
        this.favouriteList = favouriteList;
    }

    public void addFavourite(NaturalEvent naturalEvent){
        favouriteList.addNaturalEvent(naturalEvent);
    }
}
