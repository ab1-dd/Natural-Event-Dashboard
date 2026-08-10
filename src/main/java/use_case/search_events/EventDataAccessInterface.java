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

    /**
     * @param eventId an EONET event id (e.g. "EONET_21965")
     * @return the single event matching that id
     */
    NaturalEvent fetchEventById(String eventId) throws EventFetchException;
}
