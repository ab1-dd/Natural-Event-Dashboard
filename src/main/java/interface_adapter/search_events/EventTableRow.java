package interface_adapter.search_events;

import entity.NaturalEvent;

/**
 * A display-ready view of a NaturalEvent for the results "JTable".
 */
public class EventTableRow {
    private final NaturalEvent rawEvent;
    private final String eventId;
    private final String title;
    private final String category;
    private final String status;
    private final String eventDate;
    private final String coordinates;

    public EventTableRow(NaturalEvent rawEvent, String eventId, String title, String category, String status,
                          String eventDate, String coordinates) {
        this.rawEvent = rawEvent;
        this.eventId = eventId;
        this.title = title;
        this.category = category;
        this.status = status;
        this.eventDate = eventDate;
        this.coordinates = coordinates;
    }
    public NaturalEvent getRawEvent() {
        return rawEvent;
    }

    public String getEventId() {
        return eventId;
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
}
