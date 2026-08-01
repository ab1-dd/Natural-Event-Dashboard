package use_case.search_events;

/**
 * Input boundary for the "search events" use case. The controller depends on
 * this interface.
 */
public interface SearchEventsInputBoundary {
    void execute(SearchEventsInputData inputData);
}
