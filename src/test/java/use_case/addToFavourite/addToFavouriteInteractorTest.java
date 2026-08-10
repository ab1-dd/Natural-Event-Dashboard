package use_case.addToFavourite;

import entity.FavouriteList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class addToFavouriteInteractorTest {

    private FavouriteList favouriteList;
    private TestPresenter testPresenter;
    private AddToFavouriteInteractor interactor;

    // Mock presenter to capture output, same pattern used by the other interactor tests.
    private static class TestPresenter implements AddToFavouriteOutputBoundary {
        boolean presentCalled = false;
        AddToFavouriteOutputData outputData = null;

        @Override
        public void present(AddToFavouriteOutputData outputData) {
            this.presentCalled = true;
            this.outputData = outputData;
        }
    }

    @BeforeEach
    void setUp() {
        favouriteList = new FavouriteList();
        testPresenter = new TestPresenter();
        interactor = new AddToFavouriteInteractor(favouriteList, testPresenter);
    }

    @Test
    void testAddFavouriteSuccess() {
        interactor.execute(new AddToFavouriteInputData("EONET_101"));

        assertTrue(testPresenter.presentCalled, "present was not called");
        assertTrue(testPresenter.outputData.isSuccess(), "expected success on first add");

        assertEquals(1, favouriteList.getNaturalEventList().size());
        assertTrue(favouriteList.contain("EONET_101"));
    }

    @Test
    void testAddFavouriteDuplicatePrevented() {
        interactor.execute(new AddToFavouriteInputData("EONET_101")); // adding first time

        interactor.execute(new AddToFavouriteInputData("EONET_101")); // adding the same id again

        assertTrue(testPresenter.presentCalled, "present was not called");
        assertFalse(testPresenter.outputData.isSuccess(), "expected failure on duplicate add");

        // Still only one entry in the favourite list.
        assertEquals(1, favouriteList.getNaturalEventList().size());
    }

    @Test
    void testAddMultipleDifferentEvents() {
        interactor.execute(new AddToFavouriteInputData("EONET_101"));
        interactor.execute(new AddToFavouriteInputData("EONET_102"));

        assertEquals(2, favouriteList.getNaturalEventList().size());
        assertTrue(favouriteList.contain("EONET_101"));
        assertTrue(favouriteList.contain("EONET_102"));
    }
}
