package entity;

import java.util.List;

/**
 * The representation of an Event in our program.
 */
public class NaturalEvent {
    private String eventId;
    private String title;
    private String eventDate;
    private String categoryID;
    private String sourceLinks;
    private boolean isClosed;
    private List<EventLocation> eventLocation;

    public NaturalEvent(String eventId, String title, String eventDate, String categoryID, String sourceLinks, boolean isClosed, List<EventLocation> eventLocation){
        this.eventId = eventId;
        this.title = title;
        this.categoryID = categoryID;
        this.sourceLinks = sourceLinks;
        this.isClosed = isClosed;
        this.eventLocation =eventLocation;
    }

    public boolean isOpen(){
        return !isClosed;
    }

    public boolean hasCategory(){
        return categoryID != null;
    }

    public String getEventDate() {
        return eventDate;
    }

    public List<EventLocation> getEventLocation() {
        return eventLocation;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public String getSourceLinks() {
        return sourceLinks;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getTitle() {
        return title;
    }

    public String getEventId() {
        return eventId;
    }
}
