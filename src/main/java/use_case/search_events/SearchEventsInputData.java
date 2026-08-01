package use_case.search_events;

import entity.EventFilter;

import java.time.LocalDate;

/**
 * Input data for the "search events" use case. This is what the controller
 * builds from raw UI input before handing off to the interactor.
 */
public class SearchEventsInputData {
    private final String category;
    private final EventFilter.StatusFilter statusFilter;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Integer resultLimit;

    public SearchEventsInputData(String category, EventFilter.StatusFilter statusFilter,
                                  LocalDate startDate, LocalDate endDate, Integer resultLimit) {
        this.category = category;
        this.statusFilter = statusFilter;
        this.startDate = startDate;
        this.endDate = endDate;
        this.resultLimit = resultLimit;
    }

    public String getCategory() {
        return category;
    }

    public EventFilter.StatusFilter getStatusFilter() {
        return statusFilter;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getResultLimit() {
        return resultLimit;
    }
}
