package use_case.viewEventDetail;

public class ViewEventDetailOutputData {

    private final String title;
    private final String category;
    private final String status;
    private final String eventDate;
    private final String coordinates;
    private final String sourceLink;
    private final boolean useCaseFailed;

    public ViewEventDetailOutputData(String title, String category, String status, String eventDate,
                                      String coordinates, String sourceLink, boolean useCaseFailed) {
        this.title = title;
        this.category = category;
        this.status = status;
        this.eventDate = eventDate;
        this.coordinates = coordinates;
        this.sourceLink = sourceLink;
        this.useCaseFailed = useCaseFailed;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
