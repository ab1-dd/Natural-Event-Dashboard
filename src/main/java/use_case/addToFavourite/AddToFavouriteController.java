package use_case.addToFavourite;

import entity.NaturalEvent;

public class AddToFavouriteController {
    private final AddToFavouriteInputBoundary interactor;

    public AddToFavouriteController(AddToFavouriteInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(NaturalEvent event) {
        interactor.addFavourite(event);
    }
}
