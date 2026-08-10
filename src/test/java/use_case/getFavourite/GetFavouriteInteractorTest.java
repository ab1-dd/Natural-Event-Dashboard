package use_case.getFavourite;

import entity.FavouriteList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetFavouriteInteractorTest {

    @Test
    void executeWithPopulatedListTest() {
        // Arrange: Create a FavouriteList with pre-existing event IDs
        FavouriteList favouriteList = new FavouriteList();
        favouriteList.addNaturalEvent("E100");
        favouriteList.addNaturalEvent("E200");

        GetFavouriteInputData inputData = new GetFavouriteInputData();

        // Create an Output Boundary implementation to capture and assert the retrieved data
        GetFavouriteOutputBoundary presenter = new GetFavouriteOutputBoundary() {
            @Override
            public void present(GetFavouriteOutputData outputData) {
                // Assert that outputData contains the expected event IDs and size
                List<String> resultList = outputData.getFavouriteEventIds();
                assertEquals(2, resultList.size());
                assertEquals("E100", resultList.get(0));
                assertEquals("E200", resultList.get(1));
            }
        };

        // Act: Initialize the Interactor and execute the use case
        GetFavouriteInteractor interactor = new GetFavouriteInteractor(favouriteList, presenter);
        interactor.execute(inputData);
    }

    @Test
    void executeWithEmptyListTest() {
        // Arrange: Create an empty FavouriteList
        FavouriteList favouriteList = new FavouriteList();
        GetFavouriteInputData inputData = new GetFavouriteInputData();

        // Create an Output Boundary implementation expecting an empty list
        GetFavouriteOutputBoundary presenter = new GetFavouriteOutputBoundary() {
            @Override
            public void present(GetFavouriteOutputData outputData) {
                // Assert that the returned list is empty but non-null
                assertNotNull(outputData.getFavouriteEventIds());
                assertTrue(outputData.getFavouriteEventIds().isEmpty());
            }
        };

        // Act: Initialize the Interactor and execute the use case
        GetFavouriteInteractor interactor = new GetFavouriteInteractor(favouriteList, presenter);
        interactor.execute(inputData);
    }
}