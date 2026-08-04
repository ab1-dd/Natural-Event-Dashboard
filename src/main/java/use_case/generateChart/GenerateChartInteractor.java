package use_case.generateChart;

import entity.NaturalEvent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class GenerateChartInteractor implements GenerateChartInputBoundary {

    private final GenerateChartOutputBoundary presenter;

    public GenerateChartInteractor(GenerateChartOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(GenerateChartInputData inputData) {
        List<NaturalEvent> events = inputData.getEvents();
        int daysPerUnit = inputData.getDaysPerUnit();

        if (events == null || events.isEmpty()) {
            presenter.prepareFailView("No event data available to plot chart.");
            return;
        }

        if (daysPerUnit <= 0) {
            presenter.prepareFailView("Days per unit must be greater than 0.");
            return;
        }

        // Convert the raw date data (String) to local date
        List<LocalDate> dates = new ArrayList<>();
        for (NaturalEvent event : events) {
            try {
                String rawDate = event.getEventDate();
                if (rawDate != null && rawDate.length() >= 10) {
                    dates.add(LocalDate.parse(rawDate.substring(0, 10)));
                }
            } catch (Exception ignoreIt) {} // well, even there is an exception, just ignore it
        }

        if (dates.isEmpty()) {
            presenter.prepareFailView("Failed to parse valid dates from events.");
            return;
        }

        // The reason for using local date is it can be used by sort
        dates.sort(Comparator.naturalOrder());
        LocalDate minDate = dates.get(0);
        LocalDate maxDate = dates.get(dates.size() - 1);

        // Calculate total days between first date and final date of the selected events,
        // using long just in case it is "long" :-)
        long totalDays = ChronoUnit.DAYS.between(minDate, maxDate) + 1;
        // calculate how many bins we need in the graph
        int numBins = (int) Math.ceil((double) totalDays / daysPerUnit);
        // just in case
        if (numBins <= 0) numBins = 1;

        // calculate frequency
        int[] counts = new int[numBins];
        for (LocalDate date : dates) {
            // calculate how many days between minDate and the current one
            // here offset using between without +1,
            // so the exactly [daysPerUnit]th date will be count into the first bin (index 0)
            long offset = ChronoUnit.DAYS.between(minDate, date);
            int binIndex = (int) (offset / daysPerUnit);
            if (binIndex >= numBins) binIndex = numBins - 1; // just in case
            counts[binIndex]++;
        }

        // generate x-axis label
        List<String> labels = new ArrayList<>();
        // make the formate MM-dd, maybe having years here will make each bin too wide
        // second version: using yy-MM-dd, because some time I was confused by the chart :-(
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yy-MM-dd");
        int maxCount = 0;

        // find the max count, so we know how high wee need in the chart
        for (int i = 0; i < numBins; i++) {
            LocalDate binStart = minDate.plusDays((long) i * daysPerUnit);
            labels.add(binStart.format(fmt));
            if (counts[i] > maxCount) {
                maxCount = counts[i];
            }
        }

        // pack OutputData up, and send them to Presenter
        GenerateChartOutputData outputData = new GenerateChartOutputData(labels, counts, maxCount, false);
        presenter.prepareSuccessView(outputData);
    }
}
