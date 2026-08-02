package interface_adapter.addToFavourite;

import entity.NaturalEvent;
import use_case.addToFavourite.AddToFavouriteInputBoundary;

public class AddToFavouriteController {
    private final AddToFavouriteInputBoundary interactor;

    public AddToFavouriteController(AddToFavouriteInputBoundary interactor) {
        this.interactor = interactor;
    }

    public boolean execute(NaturalEvent event) {
        return interactor.addFavourite(event);
    }
}
