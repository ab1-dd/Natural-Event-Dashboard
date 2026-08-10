package interface_adapter.GetFavourite;

import use_case.getFavourite.GetFavouriteInputBoundary;
import use_case.getFavourite.GetFavouriteInputData;

public class GetFavouriteController {
    private final GetFavouriteInputBoundary inputBoundary;

    public GetFavouriteController(
            GetFavouriteInputBoundary inputBoundary) {

        this.inputBoundary = inputBoundary;
    }

    public void execute() {

        GetFavouriteInputData inputData =
                new GetFavouriteInputData();

        inputBoundary.execute(inputData);
    }
}
