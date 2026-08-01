package interface_adapter.search_events;

import java.util.Collections;
import java.util.List;

/**
 * Everything the search view needs to render at a given moment: either a
 * fresh list of rows, or an error message from a failed search.
 */
public class SearchState {
    private final List<EventTableRow> rows;
    private final String errorMessage;

    public SearchState() {
        this(Collections.emptyList(), null);
    }

    public SearchState(List<EventTableRow> rows, String errorMessage) {
        this.rows = rows;
        this.errorMessage = errorMessage;
    }

    public List<EventTableRow> getRows() {
        return rows;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean hasError() {
        return errorMessage != null && !errorMessage.isBlank();
    }
}
