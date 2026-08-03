package use_case.addToFavourite;

import entity.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddToFavouriteInteractorTest {

    private FavouriteList favouriteList;
    private TestPresenter testPresenter;
    private AddToFavouriteInteractor interactor;

    // 💡 Mock Presenter to capture output
    private static class TestPresenter implements AddToFavouriteOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        String successTitle = null;
        String errorMessage = null;

        @Override
        public void prepareSuccessView(String eventTitle) {
            this.successCalled = true;
            this.successTitle = eventTitle;
        }

        @Override
        public void prepareFailView(String eventTitle, String errorMessage) {
            this.failCalled = true;
            this.errorMessage = errorMessage;
        }
    }

    // set up the environment
    @BeforeEach
    void setUp() {
        favouriteList = new FavouriteList(new ArrayList<>());
        testPresenter = new TestPresenter();
        interactor = new AddToFavouriteInteractor(favouriteList, testPresenter);
    }

    // helper function for creating a Natural Event.
    private NaturalEvent createTestEvent(String eventId, String title) {
        // create coordinate (PointEventCoordinates)
        EventCoordinates coords = new PointEventCoordinates(43.65, 79.38, "2026-08-01");

        // Create event location object
        EventLocation location = new EventLocation("2026-08-01", coords, 100.0, "acres");

        List<EventLocation> locations = new ArrayList<>();
        locations.add(location);

        // create NaturalEvent
        return new NaturalEvent(
                eventId,
                title,
                "2026-08-01",
                "wildfires",
                "https://eonet.gsfc.nasa.gov/api/v3/events/" + eventId,
                false,
                locations
        );
    }

    @Test
    void testAddFavouriteSuccess() {
        // use the helper function to create natural Event
        NaturalEvent event = createTestEvent("EONET_101", "Wildfire in California");

        // Do the adding function
        interactor.addFavourite(event);

        // Assert
        assertTrue(testPresenter.successCalled, "prepareSuccessView is mistakenly not called");
        assertFalse(testPresenter.failCalled, "prepareFailView is mistakenly called");
        assertEquals("Wildfire in California", testPresenter.successTitle);

        assertEquals(1, favouriteList.getNaturalEventList().size());
        assertEquals(event, favouriteList.getNaturalEventList().get(0));
    }

    @Test
    void testAddFavouriteDuplicatePrevented() {
        // using the helper function
        NaturalEvent event = createTestEvent("EONET_101", "Wildfire in California");
        interactor.addFavourite(event); // adding first event

        // reset Presenter, make it record the second adding failed or not
        testPresenter.successCalled = false;

        // adding the same event
        interactor.addFavourite(event);

        // Assert
        assertFalse(testPresenter.successCalled, "successCalled is True");
        assertTrue(testPresenter.failCalled, "failCalled is False");
        assertNotNull(testPresenter.errorMessage);

        // Check if the amount of element in FavouriteList is still one.
        assertEquals(1, favouriteList.getNaturalEventList().size());
    }
}