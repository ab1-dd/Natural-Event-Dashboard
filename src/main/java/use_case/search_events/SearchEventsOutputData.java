package use_case.search_events;

import entity.NaturalEvent;

import java.util.List;

/**
 * Output data for the "search events" use case: the matching events, ready
 * for a presenter to turn into a view model.
 */
public class SearchEventsOutputData {
    private final List<NaturalEvent> events;

    public SearchEventsOutputData(List<NaturalEvent> events) {
        this.events = events;
    }

    public List<NaturalEvent> getEvents() {
        return events;
    }
}
