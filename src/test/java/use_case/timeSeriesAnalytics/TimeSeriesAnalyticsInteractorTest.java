package use_case.timeSeriesAnalytics;

import entity.EventCoordinates;
import entity.EventLocation;
import entity.NaturalEvent;
import entity.PointEventCoordinates;
import entity.TimeSeriesReport.Granularity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeSeriesAnalyticsInteractorTest {

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    private TestPresenter testPresenter;
    private TimeSeriesAnalyticsInteractor interactor;

    // Mock presenter to capture output, same pattern used by the addToFavourite tests.
    private static class TestPresenter implements TimeSeriesAnalyticsOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        TimeSeriesAnalyticsOutputData successData = null;
        String errorMessage = null;

        @Override
        public void prepareSuccessView(TimeSeriesAnalyticsOutputData outputData) {
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
        interactor = new TimeSeriesAnalyticsInteractor(testPresenter);
    }

    private NaturalEvent createTestEvent(String eventId, LocalDate eventDate) {
        String dateString = eventDate.format(ISO_DATE);
        EventCoordinates coords = new PointEventCoordinates(43.65, 79.38, dateString);
        EventLocation location = new EventLocation(dateString, coords, 100.0, "acres");

        List<EventLocation> locations = new ArrayList<>();
        locations.add(location);

        return new NaturalEvent(eventId, "Test Event " + eventId, dateString, "wildfires",
                "https://eonet.gsfc.nasa.gov/api/v3/events/" + eventId, false, locations);
    }

    @Test
    void testWeeklyAggregation_countsEventsInCorrectWeekBucket() {
        LocalDate today = LocalDate.now();
        // Anchor to the Monday of the current week so this test doesn't depend
        // on which day of the week it happens to run.
        LocalDate currentWeekMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Two events in "this week's" bucket (Monday of this week, and today --
        // both always fall in the same calendar week and are never in the
        // future), one event 3 weeks back.
        List<NaturalEvent> events = new ArrayList<>();
        events.add(createTestEvent("E1", currentWeekMonday));
        events.add(createTestEvent("E2", today));
        events.add(createTestEvent("E3", currentWeekMonday.minusWeeks(3)));

        interactor.execute(new TimeSeriesAnalyticsInputData(events, 90, Granularity.WEEK));

        assertTrue(testPresenter.successCalled, "prepareSuccessView was not called");
        assertFalse(testPresenter.failCalled, "prepareFailView was mistakenly called");

        TimeSeriesAnalyticsOutputData data = testPresenter.successData;
        int totalCounted = 0;
        for (int count : data.getCounts()) {
            totalCounted += count;
        }
        // All 3 events fall within the 90-day window, so all should be counted somewhere.
        assertEquals(3, totalCounted);

        // The most recent bucket (last in the ordered list) should hold the two "this week" events.
        int lastBucketCount = data.getCounts()[data.getCounts().length - 1];
        assertEquals(2, lastBucketCount);
    }

    @Test
    void testDailyGranularity_bucketsPerCalendarDay() {
        LocalDate today = LocalDate.now();

        List<NaturalEvent> events = new ArrayList<>();
        events.add(createTestEvent("E1", today));
        events.add(createTestEvent("E2", today));
        events.add(createTestEvent("E3", today.minusDays(1)));

        interactor.execute(new TimeSeriesAnalyticsInputData(events, 30, Granularity.DAY));

        assertTrue(testPresenter.successCalled);
        TimeSeriesAnalyticsOutputData data = testPresenter.successData;

        int[] counts = data.getCounts();
        assertEquals(2, counts[counts.length - 1], "today's bucket should have 2 events");
        assertEquals(1, counts[counts.length - 2], "yesterday's bucket should have 1 event");
    }

    @Test
    void testEmptyEventList_callsPrepareFailView() {
        interactor.execute(new TimeSeriesAnalyticsInputData(new ArrayList<>(), 90, Granularity.WEEK));

        assertFalse(testPresenter.successCalled);
        assertTrue(testPresenter.failCalled);
        assertNotNull(testPresenter.errorMessage);
    }

    @Test
    void testAllEventsOutsideWindow_callsPrepareFailView() {
        LocalDate today = LocalDate.now();
        List<NaturalEvent> events = new ArrayList<>();
        events.add(createTestEvent("E1", today.minusDays(200)));

        interactor.execute(new TimeSeriesAnalyticsInputData(events, 30, Granularity.WEEK));

        assertFalse(testPresenter.successCalled);
        assertTrue(testPresenter.failCalled);
        assertNotNull(testPresenter.errorMessage);
    }

    @Test
    void testInvalidWindowDays_callsPrepareFailView() {
        LocalDate today = LocalDate.now();
        List<NaturalEvent> events = new ArrayList<>();
        events.add(createTestEvent("E1", today));

        interactor.execute(new TimeSeriesAnalyticsInputData(events, 0, Granularity.WEEK));

        assertFalse(testPresenter.successCalled);
        assertTrue(testPresenter.failCalled);
    }

    @Test
    void testNullGranularity_callsPrepareFailView() {
        LocalDate today = LocalDate.now();
        List<NaturalEvent> events = new ArrayList<>();
        events.add(createTestEvent("E1", today));

        interactor.execute(new TimeSeriesAnalyticsInputData(events, 90, null));

        assertFalse(testPresenter.successCalled);
        assertTrue(testPresenter.failCalled);
    }
}
