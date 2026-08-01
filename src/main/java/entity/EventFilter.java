package entity;

import java.time.LocalDate;

/**
 * A reusable set of search settings for natural events.
 * This is the entity used for a one-off search and a saved WatchProfile.
 */
public class EventFilter {

    /**
     * Mirrors EONET's "status" query parameter: open events only, closed events
     * only, or both.
     */
    public enum StatusFilter {
        ACTIVE,
        CLOSED,
        ALL;

        /**
         * @return the value EONET expects for its "status" query parameter.
         */
        public String toQueryValue() {
            switch (this) {
                case ACTIVE:
                    return "open";
                case CLOSED:
                    return "closed";
                default:
                    return "all";
            }
        }
    }

    private final String category;
    private final StatusFilter statusFilter;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Integer resultLimit;

    /**
     * @param category     an EONET category id (e.g. "wildfires"), or null/blank for all categories
     * @param statusFilter whether to include active events, closed events, or both
     * @param startDate    start of the date range, or null for no lower bound
     * @param endDate      end of the date range, or null for no upper bound
     * @param resultLimit  maximum number of events to return, or null for no limit
     */
    public EventFilter(String category, StatusFilter statusFilter, LocalDate startDate,
                        LocalDate endDate, Integer resultLimit) {
        this.category = category;
        this.statusFilter = statusFilter;
        this.startDate = startDate;
        this.endDate = endDate;
        this.resultLimit = resultLimit;
    }

    /**
     * Factory for "all categories, active only, last N days" searches,
     * for the dashboard's default/central search flow.
     */
    public static EventFilter lastNDaysActive(int days, Integer resultLimit) {
        LocalDate today = LocalDate.now();
        return new EventFilter(null, StatusFilter.ACTIVE, today.minusDays(days), today, resultLimit);
    }

    public boolean hasCategory() {
        return category != null && !category.isBlank();
    }

    public String getCategory() {
        return category;
    }

    public StatusFilter getStatusFilter() {
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
