package use_case.search_events;

import entity.EventFilter;
import entity.NaturalEvent;

import java.util.List;

/**
 * Boundary the interactor uses to fetch events, without knowing whether the
 * implementation talks to the live EONET API, a cache, or a test double.
 */
public interface EventDataAccessInterface {
    List<NaturalEvent> fetchEvents(EventFilter filter) throws EventFetchException;
}
