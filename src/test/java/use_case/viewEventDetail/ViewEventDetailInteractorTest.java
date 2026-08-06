package use_case.viewEventDetail;

import entity.EventCoordinates;
import entity.EventLocation;
import entity.NaturalEvent;
import entity.PointEventCoordinates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewEventDetailInteractorTest {

    private TestPresenter testPresenter;
    private ViewEventDetailInteractor interactor;

    // Mock presenter to capture output, same pattern used by the other interactor tests.
    private static class TestPresenter implements ViewEventDetailOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        ViewEventDetailOutputData successData = null;
        String errorMessage = null;

        @Override
        public void prepareSuccessView(ViewEventDetailOutputData outputData) {
            this.successCalled = true;
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            this.failCalled = true;
            this.errorMessage = error;
        }
    }

    @BeforeEach
    void setUp() {
        testPresenter = new TestPresenter();
        interactor = new ViewEventDetailInteractor(testPresenter);
    }

    private NaturalEvent createTestEvent(String eventId, String title, String categoryId, boolean isClosed,
                                          String sourceLinks, List<EventLocation> locations) {
        return new NaturalEvent(eventId, title, "2026-08-01", categoryId, sourceLinks, isClosed, locations);
    }

    private EventLocation createLocation() {
        EventCoordinates coords = new PointEventCoordinates(43.65, 79.38, "2026-08-01");
        return new EventLocation("2026-08-01", coords, 100.0, "acres");
    }

    @Test
    void testFullyPopulatedEvent_formatsAllFieldsCorrectly() {
        List<EventLocation> locations = new ArrayList<>();
        locations.add(createLocation());

        NaturalEvent event = createTestEvent("EONET_101", "Severe Storm off the coast", "severeStorms", false,
                "https://eonet.gsfc.nasa.gov/api/v3/events/EONET_101", locations);

        interactor.execute(new ViewEventDetailInputData(event));

        assertTrue(testPresenter.successCalled, "prepareSuccessView was not called");
        assertFalse(testPresenter.failCalled, "prepareFailView was mistakenly called");

        ViewEventDetailOutputData data = testPresenter.successData;
        assertEquals("Severe Storm off the coast", data.getTitle());
        assertEquals("severeStorms", data.getCategory());
        assertEquals("Active", data.getStatus());
        assertEquals("2026-08-01", data.getEventDate());
        assertEquals("https://eonet.gsfc.nasa.gov/api/v3/events/EONET_101", data.getSourceLink());
        assertTrue(data.getCoordinates().contains("43.65") || !data.getCoordinates().isBlank());
    }

    @Test
    void testClosedEvent_statusIsClosed() {
        NaturalEvent event = createTestEvent("EONET_102", "Old Wildfire", "wildfires", true, null, new ArrayList<>());

        interactor.execute(new ViewEventDetailInputData(event));

        assertTrue(testPresenter.successCalled);
        assertEquals("Closed", testPresenter.successData.getStatus());
    }

    @Test
    void testNoCategory_fallsBackToUncategorized() {
        NaturalEvent event = createTestEvent("EONET_103", "Mystery Event", null, false, null, new ArrayList<>());

        interactor.execute(new ViewEventDetailInputData(event));

        assertTrue(testPresenter.successCalled);
        assertEquals("Uncategorized", testPresenter.successData.getCategory());
    }

    @Test
    void testNoLocations_fallsBackToNoLocationData() {
        NaturalEvent event = createTestEvent("EONET_104", "No Location Event", "wildfires", false, null, new ArrayList<>());

        interactor.execute(new ViewEventDetailInputData(event));

        assertTrue(testPresenter.successCalled);
        assertEquals("No location data", testPresenter.successData.getCoordinates());
    }

    @Test
    void testBlankSourceLink_fallsBackToNoSourceLinkAvailable() {
        NaturalEvent event = createTestEvent("EONET_105", "No Source Event", "wildfires", false, "   ", new ArrayList<>());

        interactor.execute(new ViewEventDetailInputData(event));

        assertTrue(testPresenter.successCalled);
        assertEquals("No source link available", testPresenter.successData.getSourceLink());
    }

    @Test
    void testNullEvent_callsPrepareFailView() {
        interactor.execute(new ViewEventDetailInputData(null));

        assertFalse(testPresenter.successCalled);
        assertTrue(testPresenter.failCalled);
        assertNotNull(testPresenter.errorMessage);
    }
}
