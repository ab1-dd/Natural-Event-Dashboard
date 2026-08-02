package use_case.addToFavourite;

import entity.NaturalEvent;

public interface AddToFavouriteInputBoundary {
    boolean addFavourite(NaturalEvent naturalEvent);
}
