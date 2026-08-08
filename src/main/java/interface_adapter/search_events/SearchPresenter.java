package interface_adapter.search_events;

import entity.EventLocation;
import entity.NaturalEvent;
import use_case.search_events.SearchEventsOutputBoundary;
import use_case.search_events.SearchEventsOutputData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts use case output into a SearchState the view can render, and
 * pushes it into the SearchViewModel.
 */
public class SearchPresenter implements SearchEventsOutputBoundary {

    private final SearchViewModel viewModel;

    public SearchPresenter(SearchViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SearchEventsOutputData outputData) {
        List<EventTableRow> rows = new ArrayList<>();
        Map<String, NaturalEvent> eventsById = new LinkedHashMap<>();
        for (NaturalEvent event : outputData.getEvents()) {
            rows.add(toRow(event));
            eventsById.put(event.getEventId(), event);
        }
        viewModel.setState(new SearchState(rows, eventsById, null));
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setState(new SearchState(List.of(), Map.of(), errorMessage));
    }

    private EventTableRow toRow(NaturalEvent event) {
        String status = event.isOpen() ? "Active" : "Closed";
        String coordinates = "No location data";
        if (event.getEventLocation() != null && !event.getEventLocation().isEmpty()) {
            EventLocation firstLocation = event.getEventLocation().get(0);
            coordinates = firstLocation.getCoordinates();
        }

        return new EventTableRow(
                event.getEventId(),
                event.getTitle(),
                event.hasCategory() ? event.getCategoryID() : "Uncategorized",
                status,
                event.getEventDate(),
                coordinates
        );
    }
}
