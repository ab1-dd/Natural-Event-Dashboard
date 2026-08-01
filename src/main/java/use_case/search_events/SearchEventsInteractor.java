package use_case.search_events;

import entity.EventFilter;
import entity.NaturalEvent;

import java.util.List;

/**
 * Implements the "search events" use case: Maya opens the app, picks a
 * category/status/date-range filter, and sees matching events.
 */
public class SearchEventsInteractor implements SearchEventsInputBoundary {

    private final EventDataAccessInterface eventDataAccess;
    private final SearchEventsOutputBoundary presenter;

    public SearchEventsInteractor(EventDataAccessInterface eventDataAccess,
                                   SearchEventsOutputBoundary presenter) {
        this.eventDataAccess = eventDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(SearchEventsInputData inputData) {
        EventFilter filter = new EventFilter(
                inputData.getCategory(),
                inputData.getStatusFilter(),
                inputData.getStartDate(),
                inputData.getEndDate(),
                inputData.getResultLimit()
        );

        try {
            List<NaturalEvent> events = eventDataAccess.fetchEvents(filter);
            presenter.prepareSuccessView(new SearchEventsOutputData(events));
        } catch (EventFetchException exception) {
            presenter.prepareFailView(exception.getMessage());
        }
    }
}
