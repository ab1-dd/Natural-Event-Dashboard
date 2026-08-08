package interface_adapter.search_events;

import entity.NaturalEvent;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Everything the search view needs to render at a given moment: either a
 * fresh list of rows, or an error message from a failed search.
 * Alongside the display-ready rows, this holds the raw NaturalEvent entities
 * from the last search, keyed by eventId, so the view can look one up (e.g.
 * for "view detail" or "add to favourite") without EventTableRow itself
 * having to carry an entity reference.
 */
public class SearchState {
    private final List<EventTableRow> rows;
    private final Map<String, NaturalEvent> eventsById;
    private final String errorMessage;

    public SearchState() {
        this(Collections.emptyList(), Collections.emptyMap(), null);
    }

    public SearchState(List<EventTableRow> rows, Map<String, NaturalEvent> eventsById, String errorMessage) {
        this.rows = rows;
        this.eventsById = eventsById;
        this.errorMessage = errorMessage;
    }

    public List<EventTableRow> getRows() {
        return rows;
    }

    /**
     * @param eventId the id of a row from {@link #getRows()}
     * @return the full NaturalEvent behind that row, or null if not found
     */

    public NaturalEvent getEvent(String eventId) {
        return eventsById.get(eventId);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean hasError() {
        return errorMessage != null && !errorMessage.isBlank();
    }
}
