package use_case.addToFavourite;

public interface AddToFavouriteOutputBoundary {
    void prepareSuccessView(String eventTitle);
    void prepareFailView(String eventTitle, String errorMessage);
}
