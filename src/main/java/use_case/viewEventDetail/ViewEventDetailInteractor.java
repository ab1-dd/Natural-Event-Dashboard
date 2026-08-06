package use_case.viewEventDetail;

import entity.EventLocation;
import entity.NaturalEvent;

/**
 * Formats a selected NaturalEvent into display-ready detail fields.
 *
 * Satisfies User Story 2: "Daniel selects a storm event from the results
 * table. The program displays detailed information, including the title,
 * category, event status, event date, coordinates, and source link if
 * available."
 *
 * Formatting rules mirror SearchPresenter's row formatting (same "Active"/
 * "Closed" status wording, same "Uncategorized" / "No location data"
 * fallbacks) so the detail view stays consistent with the results table.
 */
public class ViewEventDetailInteractor implements ViewEventDetailInputBoundary {

    private static final String NO_LOCATION_DATA = "No location data";
    private static final String UNCATEGORIZED = "Uncategorized";
    private static final String NO_SOURCE_LINK = "No source link available";

    private final ViewEventDetailOutputBoundary presenter;

    public ViewEventDetailInteractor(ViewEventDetailOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewEventDetailInputData inputData) {
        NaturalEvent event = inputData == null ? null : inputData.getEvent();

        if (event == null) {
            presenter.prepareFailView("No event selected.");
            return;
        }

        String status = event.isOpen() ? "Active" : "Closed";
        String category = event.hasCategory() ? event.getCategoryID() : UNCATEGORIZED;

        String coordinates = NO_LOCATION_DATA;
        if (event.getEventLocation() != null && !event.getEventLocation().isEmpty()) {
            EventLocation mostRecentLocation = event.getEventLocation().get(0);
            coordinates = mostRecentLocation.getCoordinates();
        }

        String sourceLink = event.getSourceLinks();
        if (sourceLink == null || sourceLink.isBlank()) {
            sourceLink = NO_SOURCE_LINK;
        }

        ViewEventDetailOutputData outputData = new ViewEventDetailOutputData(
                event.getTitle(), category, status, event.getEventDate(), coordinates, sourceLink, false
        );
        presenter.prepareSuccessView(outputData);
    }
}
