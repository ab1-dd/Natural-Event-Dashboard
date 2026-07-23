package entity;

import java.util.List;

/**
 * The representation of an Event in our program.
 */
public class NaturalEvent {
    private String eventId;
    private String title;
    private String eventDate;
    private String categories;
    private String sourceLinks;
    private boolean isClosed;
    private List<EventLocation> eventList;

    public boolean isOpen(){
        return !isClosed;
    }

}
