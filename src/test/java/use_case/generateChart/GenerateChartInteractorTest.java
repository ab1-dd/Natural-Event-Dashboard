package use_case.generateChart;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateChartInteractorTest {

    // Test the normal case
    @Test
    void executeSuccessTest() {
        // Arrange: Create sample event map with ISO date strings
        Map<String, String> eventMap = new HashMap<>();
        eventMap.put("E1", "2026-08-01T10:00:00Z");
        eventMap.put("E2", "2026-08-03T12:00:00Z");
        eventMap.put("E3", "2026-08-05T15:00:00Z");

        GenerateChartInputData inputData = new GenerateChartInputData(eventMap, 2);

        // Anonymous presenter class to capture success output
        GenerateChartOutputBoundary presenter = new GenerateChartOutputBoundary() {
            @Override
            public void prepareSuccessView(GenerateChartOutputData outputData) {
                // Assert that chart calculations were executed properly
                assertFalse(outputData.isUseCaseFailed());
                assertTrue(outputData.getMaxCount() > 0);
                assertNotNull(outputData.getBinLabels());
                assertTrue(outputData.getCounts().length > 0);
            }

            @Override
            public void prepareFailView(String error) {
                fail("Should not call prepareFailView on valid input.");
            }
        };

        // Act
        GenerateChartInteractor interactor = new GenerateChartInteractor(presenter);
        interactor.execute(inputData);
    }


    // Test the Case that if we input a empty eventMap, will the generation failed?
    @Test
    void executeEmptyEventMapFailTest() {
        // Arrange: Empty map input
        GenerateChartInputData inputData = new GenerateChartInputData(new HashMap<>(), 5);

        GenerateChartOutputBoundary presenter = new GenerateChartOutputBoundary() {
            @Override
            public void prepareSuccessView(GenerateChartOutputData outputData) {
                fail("Should not call prepareSuccessView when event map is empty.");
            }

            @Override
            public void prepareFailView(String error) {
                // Assert error message for empty input
                assertEquals("No event data available to plot chart.", error);
            }
        };

        // Act
        GenerateChartInteractor interactor = new GenerateChartInteractor(presenter);
        interactor.execute(inputData);
    }

    // Test some illegal input like datsPerUnit <= 0
    @Test
    void executeInvalidDaysPerUnitFailTest() {
        // Arrange: Invalid daysPerUnit <= 0
        Map<String, String> eventMap = Map.of("E1", "2026-08-01");
        GenerateChartInputData inputData = new GenerateChartInputData(eventMap, 0);

        GenerateChartOutputBoundary presenter = new GenerateChartOutputBoundary() {
            @Override
            public void prepareSuccessView(GenerateChartOutputData outputData) {
                fail("Should not call prepareSuccessView when daysPerUnit is invalid.");
            }

            @Override
            public void prepareFailView(String error) {
                // Assert error message for invalid unit step
                assertEquals("Days per unit must be greater than 0.", error);
            }
        };

        // Act
        GenerateChartInteractor interactor = new GenerateChartInteractor(presenter);
        interactor.execute(inputData);
    }
}