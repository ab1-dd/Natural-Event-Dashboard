package interface_adapter.search_events;

import entity.EventFilter;
import use_case.search_events.SearchEventsInputBoundary;
import use_case.search_events.SearchEventsInputData;

import java.time.LocalDate;

/**
 * Translates raw UI input (from SearchView) into a use case call.
 */
public class SearchController {

    private final SearchEventsInputBoundary searchEventsInteractor;

    public SearchController(SearchEventsInputBoundary searchEventsInteractor) {
        this.searchEventsInteractor = searchEventsInteractor;
    }

    /**
     * @param category     an EONET category id, or null/blank for all categories
     * @param statusFilter active-only, closed-only, or all events
     * @param startDate    lower bound of the date range
     * @param endDate      upper bound of the date range
     * @param resultLimit  maximum number of events to return, or null for no limit
     */
    public void search(String category, EventFilter.StatusFilter statusFilter,
                        LocalDate startDate, LocalDate endDate, Integer resultLimit) {
        searchEventsInteractor.execute(
                new SearchEventsInputData(category, statusFilter, startDate, endDate, resultLimit));
    }

}
