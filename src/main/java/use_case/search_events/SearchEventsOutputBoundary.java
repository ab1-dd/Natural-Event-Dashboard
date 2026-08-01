package use_case.search_events;

/**
 * Output boundary for the "search events" use case. The interactor depends on
 * this interface, not on the presenter implementation, so the core use case
 * stays free of any UI or charting code.
 */
public interface SearchEventsOutputBoundary {
    void prepareSuccessView(SearchEventsOutputData outputData);

    void prepareFailView(String errorMessage);
}
