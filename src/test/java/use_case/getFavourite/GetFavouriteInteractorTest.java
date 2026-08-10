package use_case.getFavourite;

import entity.EventFilter;
import entity.FavouriteList;
import entity.NaturalEvent;
import org.junit.jupiter.api.Test;
import use_case.search_events.EventDataAccessInterface;
import use_case.search_events.EventFetchException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetFavouriteInteractorTest {

    // Helper fake DAO that only returns event or throws exception
    private static class FakeDataAccess implements EventDataAccessInterface {
        private final NaturalEvent eventToReturn;

        public FakeDataAccess(NaturalEvent eventToReturn) {
            this.eventToReturn = eventToReturn;
        }

        @Override
        public List<NaturalEvent> fetchEvents(EventFilter filter) {
            // dummy
            throw new UnsupportedOperationException("Dont need this");
        }



        @Override
        public NaturalEvent fetchEventById(String eventId) throws EventFetchException {
            if (eventToReturn != null && eventId.equals(eventToReturn.getEventId())) {
                return eventToReturn;
            }
            throw new EventFetchException("Event not found");
        }

        // IDE Quick-Fix: If your interface has other methods, let IDE auto-generate them here
    }

    @Test
    void testExecuteSuccess() {
        // Arrange
        FavouriteList favouriteList = new FavouriteList();
        favouriteList.addNaturalEvent("E001");

        // Construct a dummy event matching your NaturalEvent constructor signature
        NaturalEvent testEvent = new NaturalEvent("E001", "Wildfire A", "Fire", "2026-08-10", "Active", false, new ArrayList<>());

        EventDataAccessInterface dataAccess = new FakeDataAccess(testEvent);
        GetFavouriteInputData inputData = new GetFavouriteInputData();

        List<GetFavouriteOutputData> capturedOutput = new ArrayList<>();
        GetFavouriteOutputBoundary presenter = outputData -> capturedOutput.add(outputData);

        // Act
        GetFavouriteInteractor interactor = new GetFavouriteInteractor(favouriteList, presenter, dataAccess);
        interactor.execute(inputData);

        // Assert
        assertFalse(capturedOutput.isEmpty());
        List<List<String>> result = capturedOutput.get(0).getFavouriteEventInfo();
        assertEquals(1, result.size());
        assertEquals("Wildfire A", result.get(0).get(0));
    }

    @Test
    void testExecuteWithFetchException() {
        // Arrange
        FavouriteList favouriteList = new FavouriteList();
        favouriteList.addNaturalEvent("INVALID_ID");

        EventDataAccessInterface dataAccess = new FakeDataAccess(null);
        GetFavouriteInputData inputData = new GetFavouriteInputData();

        List<GetFavouriteOutputData> capturedOutput = new ArrayList<>();
        GetFavouriteOutputBoundary presenter = outputData -> capturedOutput.add(outputData);

        // Act
        GetFavouriteInteractor interactor = new GetFavouriteInteractor(favouriteList, presenter, dataAccess);

        // Assert: Ensure exception is caught internally without crashing
        assertDoesNotThrow(() -> interactor.execute(inputData));
        assertTrue(capturedOutput.isEmpty());
    }
}