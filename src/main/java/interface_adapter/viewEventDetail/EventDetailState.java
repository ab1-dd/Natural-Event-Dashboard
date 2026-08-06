package interface_adapter.viewEventDetail;

public class EventDetailState {

    private String title = "";
    private String category = "";
    private String status = "";
    private String eventDate = "";
    private String coordinates = "";
    private String sourceLink = "";
    private String errorMessage = null;

    public EventDetailState() {
    }

    public EventDetailState(EventDetailState copy) {
        if (copy != null) {
            this.title = copy.title;
            this.category = copy.category;
            this.status = copy.status;
            this.eventDate = copy.eventDate;
            this.coordinates = copy.coordinates;
            this.sourceLink = copy.sourceLink;
            this.errorMessage = copy.errorMessage;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean hasError() {
        return errorMessage != null && !errorMessage.isBlank();
    }
}
