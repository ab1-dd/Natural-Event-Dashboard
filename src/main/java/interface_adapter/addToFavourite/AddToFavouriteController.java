package interface_adapter.addToFavourite;

import entity.NaturalEvent;
import use_case.addToFavourite.AddToFavouriteInputBoundary;
import use_case.addToFavourite.AddToFavouriteInputData;

public class AddToFavouriteController {
    private final AddToFavouriteInputBoundary inputBoundary;

    public AddToFavouriteController(AddToFavouriteInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void execute(String eventID) {
        AddToFavouriteInputData inputData = new AddToFavouriteInputData(eventID);

        inputBoundary.execute(inputData);
    }
}
