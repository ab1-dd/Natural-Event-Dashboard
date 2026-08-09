package use_case.addToFavourite;

public class AddToFavouriteInputData {

    private final String eventId;

    public AddToFavouriteInputData(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }
}