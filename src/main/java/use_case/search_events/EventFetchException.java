package use_case.search_events;

/**
 * Thrown when the EONET data access object cannot retrieve or parse event
 * data i.e: network failure, bad response, unexpected JSON shape, etc.
 */
public class EventFetchException extends Exception {
    public EventFetchException(String message) {
        super(message);
    }

    public EventFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
