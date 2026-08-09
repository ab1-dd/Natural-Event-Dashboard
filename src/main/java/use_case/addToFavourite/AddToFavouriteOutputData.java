package use_case.addToFavourite;

public class AddToFavouriteOutputData {

    private final boolean success;

    public AddToFavouriteOutputData(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
