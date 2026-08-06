package view;

import entity.NaturalEvent;
import entity.TimeSeriesReport.Granularity;
import interface_adapter.timeSeriesAnalytics.TimeSeriesController;
import interface_adapter.timeSeriesAnalytics.TimeSeriesState;
import interface_adapter.timeSeriesAnalytics.TimeSeriesViewModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Time-series analytics dashboard: lets the user pick a time window and a
 * granularity, then shows a line chart of event frequency over time.
 */
public class TimeSeriesView extends JDialog implements PropertyChangeListener {

    private static final String SERIES_NAME = "Events";

    private final List<NaturalEvent> events;
    private final TimeSeriesController controller;
    private final TimeSeriesViewModel viewModel;

    private final JComboBox<TimeWindowOption> timeWindowComboBox = new JComboBox<>(new TimeWindowOption[]{
            new TimeWindowOption("Past 30 Days", 30),
            new TimeWindowOption("Past 90 Days", 90),
            new TimeWindowOption("Past 1 Year", 365),
            new TimeWindowOption("Past 5 Years", 1825)
    });

    private final JComboBox<Granularity> granularityComboBox = new JComboBox<>(Granularity.values());

    private final JLabel statusLabel = new JLabel(" ");
    private final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private final ChartPanel chartPanel;

    public TimeSeriesView(Window owner, List<NaturalEvent> events, TimeSeriesController controller,
                           TimeSeriesViewModel viewModel) {
        super(owner, "Time-Series Analytics", ModalityType.MODELESS);
        this.events = events;
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setSize(750, 480);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(8, 8));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        topPanel.add(new JLabel("Time Window:"));
        topPanel.add(timeWindowComboBox);
        topPanel.add(new JLabel("Group By:"));
        topPanel.add(granularityComboBox);
        add(topPanel, BorderLayout.NORTH);

        JFreeChart chart = ChartFactory.createLineChart(
                "Event Frequency Over Time",
                "Period",
                "Number of Events",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );
        chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);

        add(statusLabel, BorderLayout.SOUTH);

        // Sara's default flow: past 90 days, grouped by week.
        timeWindowComboBox.setSelectedIndex(1);
        granularityComboBox.setSelectedItem(Granularity.WEEK);

        timeWindowComboBox.addActionListener(e -> triggerGeneration());
        granularityComboBox.addActionListener(e -> triggerGeneration());

        triggerGeneration();
    }

    private void triggerGeneration() {
        TimeWindowOption selectedWindow = (TimeWindowOption) timeWindowComboBox.getSelectedItem();
        Granularity granularity = (Granularity) granularityComboBox.getSelectedItem();
        if (selectedWindow == null || granularity == null) {
            return;
        }
        controller.generate(events, selectedWindow.days, granularity);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TimeSeriesViewModel.STATE_PROPERTY.equals(evt.getPropertyName())) {
            render(viewModel.getState());
        }
    }

    private void render(TimeSeriesState state) {
        dataset.clear();

        if (state.hasError()) {
            statusLabel.setText("Error: " + state.getErrorMessage());
            return;
        }

        List<String> labels = state.getPeriodLabels();
        int[] counts = state.getCounts();
        for (int i = 0; i < counts.length; i++) {
            dataset.addValue(counts[i], SERIES_NAME, labels.get(i));
        }
        statusLabel.setText(counts.length + " period(s) plotted.");
    }

    /**
     * Wraps a human-readable label around a "days back" value for the
     * time-window dropdown.
     */
    private static final class TimeWindowOption {
        private final String label;
        private final int days;

        private TimeWindowOption(String label, int days) {
            this.label = label;
            this.days = days;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
