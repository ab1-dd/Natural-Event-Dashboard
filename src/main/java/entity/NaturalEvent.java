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
    private EventLocation eventLocation;

    public NaturalEvent(String eventId, String title, String eventDate, String categoryID, String sourceLinks, boolean isClosed, EventLocation eventLocation){
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

}
