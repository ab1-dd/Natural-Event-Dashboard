package use_case.addToFavourite;

import entity.FavouriteList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddToFavouriteInteractorTest {

    @Test
    void successAddEventTest() {
        // Arrange: Create necessary Entities and Input Data
        FavouriteList favouriteList = new FavouriteList();
        AddToFavouriteInputData inputData = new AddToFavouriteInputData("E123");

        // Create an Output Boundary implementation to capture and assert the presenter's output
        AddToFavouriteOutputBoundary successPresenter = new AddToFavouriteOutputBoundary() {
            @Override
            public void present(AddToFavouriteOutputData outputData) {
                // Assert that the use case reports success
                assertTrue(outputData.isSuccess());
            }
        };

        // Act: Initialize the Interactor and execute the use case
        AddToFavouriteInteractor interactor = new AddToFavouriteInteractor(favouriteList, successPresenter);
        interactor.execute(inputData);

        // Assert: Verify state changes in the entity
        assertTrue(favouriteList.contain("E123"));
        assertEquals(1, favouriteList.getNaturalEventList().size());
    }

    @Test
    void failureDuplicateEventTest() {
        // Arrange: Pre-populate the favourite list with the target event ID
        FavouriteList favouriteList = new FavouriteList();
        favouriteList.addNaturalEvent("E123");

        AddToFavouriteInputData inputData = new AddToFavouriteInputData("E123");

        // Create an Output Boundary implementation expecting a failure status
        AddToFavouriteOutputBoundary failurePresenter = new AddToFavouriteOutputBoundary() {
            @Override
            public void present(AddToFavouriteOutputData outputData) {
                // Assert that the use case reports failure due to duplication
                assertFalse(outputData.isSuccess());
            }
        };

        // Act: Initialize the Interactor and execute the use case with duplicate data
        AddToFavouriteInteractor interactor = new AddToFavouriteInteractor(favouriteList, failurePresenter);
        interactor.execute(inputData);

        // Assert: Ensure list size didn't increase (no duplicate was appended)
        assertEquals(1, favouriteList.getNaturalEventList().size());
    }
}