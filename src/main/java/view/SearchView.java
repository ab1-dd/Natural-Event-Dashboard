package view;

import entity.EventFilter;
import entity.NaturalEvent;
import interface_adapter.generateChart.ChartViewModel;
import interface_adapter.generateChart.GenerateChartController;
import interface_adapter.search_events.EventTableRow;
import interface_adapter.search_events.SearchController;
import interface_adapter.search_events.SearchState;
import interface_adapter.search_events.SearchViewModel;
import interface_adapter.addToFavourite.AddToFavouriteController;
import interface_adapter.addToFavourite.AddToFavouriteViewModel;
import interface_adapter.timeSeriesAnalytics.TimeSeriesController;
import interface_adapter.timeSeriesAnalytics.TimeSeriesViewModel;
import interface_adapter.viewEventDetail.ViewEventDetailController;
import interface_adapter.viewEventDetail.EventDetailViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main search screen: category / status / look-back-days / result-limit
 * filters on top, and a results table underneath.
 *
 * This satisfies the central user story: "Maya opens the app, selects all
 * categories, chooses active events from the last 30 days, and sees matching
 * natural events in a table."
 */
public class SearchView extends JPanel implements PropertyChangeListener {

    // A representative subset of EONET's known category ids. "All Categories"
    // leaves the category filter blank so EONET returns every category.
    private static final String ALL_CATEGORIES = "All Categories";
    private static final String[] CATEGORY_OPTIONS = {
            ALL_CATEGORIES, "Wildfires", "Severe Storms", "Volcanoes", "Drought",
            "Earthquakes", "Floods", "Landslides", "Sea/Lake Ice", "Snow", "Temp Extremes"
    };

    private static final String[] STATUS_OPTIONS = {"Active", "Closed", "All"};

    private final SearchController controller;
    private final SearchViewModel viewModel;

    private final GenerateChartController generateChartController;
    private final ChartViewModel chartViewModel;

    private final TimeSeriesController timeSeriesController;
    private final TimeSeriesViewModel timeSeriesViewModel;

    private final ViewEventDetailController viewEventDetailController;
    private final EventDetailViewModel eventDetailViewModel;

    private final AddToFavouriteController addToFavouriteController;
    private final AddToFavouriteViewModel addToFavouriteViewModel;

    private final JComboBox<String> categoryComboBox = new JComboBox<>(CATEGORY_OPTIONS);
    private final JComboBox<String> statusComboBox = new JComboBox<>(STATUS_OPTIONS);
    private final JSpinner daysBackSpinner = new JSpinner(new SpinnerNumberModel(30, 1, 3650, 1));
    private final JSpinner resultLimitSpinner = new JSpinner(new SpinnerNumberModel(50, 1, 5000, 1));
    private final JButton searchButton = new JButton("Search");

    // button for add to favourite
    private final JButton favoriteButton = new JButton("❤️ Add To Favourite");

    private final JButton chartButton = new JButton("View Frequency Chart");

    private final JButton timeSeriesButton = new JButton("Time-Series Analytics");

    private final JLabel statusLabel = new JLabel(" ");

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Title", "Category", "Status", "Event Date", "Coordinates"}, 0) { //new button for addToFavourite
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable resultsTable = new JTable(tableModel);



    public SearchView(SearchController controller, SearchViewModel viewModel,
                      AddToFavouriteController addToFavouriteController,
                      AddToFavouriteViewModel addToFavouriteViewModel,
                      GenerateChartController generateChartController,
                      ChartViewModel chartViewModel,
                      TimeSeriesController timeSeriesController,
                      TimeSeriesViewModel timeSeriesViewModel,
                      ViewEventDetailController viewEventDetailController,
                      EventDetailViewModel eventDetailViewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        // controller for add to favourite
        this.addToFavouriteController = addToFavouriteController;
        this.addToFavouriteViewModel = addToFavouriteViewModel;
        this.generateChartController = generateChartController;
        this.chartViewModel = chartViewModel;
        this.timeSeriesController = timeSeriesController;
        this.timeSeriesViewModel = timeSeriesViewModel;
        this.viewEventDetailController = viewEventDetailController;
        this.eventDetailViewModel = eventDetailViewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFilterPanel(), BorderLayout.NORTH);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);
        // changed the bottom panel, put statusLabel, chart making and Favourite button together

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusLabel, BorderLayout.WEST);

        JPanel buttonGroupPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonGroupPanel.add(chartButton);
        buttonGroupPanel.add(timeSeriesButton);
        buttonGroupPanel.add(favoriteButton); // put the favourite button down right

        bottomPanel.add(buttonGroupPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        favoriteButton.addActionListener(e -> onAddToFavourite());

        chartButton.addActionListener(e -> onShowChart());

        timeSeriesButton.addActionListener(e -> onShowTimeSeries());

        searchButton.addActionListener(event -> onSearch());

        // Double-clicking a row opens the event detail view (User Story 2).
        resultsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    onShowEventDetail();
                }
            }
        });

        render(viewModel.getState());
    }

    private JPanel buildFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));

        panel.add(new JLabel("Category:"));
        panel.add(categoryComboBox);

        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);

        panel.add(new JLabel("Past days:"));
        panel.add(daysBackSpinner);

        panel.add(new JLabel("Limit:"));
        panel.add(resultLimitSpinner);

        panel.add(searchButton);

        return panel;
    }

    // function for making chart button
    private void onShowChart() {
        SearchState state = viewModel.getState();
        if (state.getRows() == null || state.getRows().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No search results to generate chart!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Map<String, String> currentEvents = new HashMap<>();
        for (EventTableRow row : state.getRows()) {
            currentEvents.put(row.getEventId(), row.getEventDate());
        }

        // open dialog window
        ChartView dialog = new ChartView(
                SwingUtilities.getWindowAncestor(this),
                currentEvents,
                generateChartController,
                chartViewModel
        );
        dialog.setVisible(true);
    }

    // function for opening the time-series analytics dashboard (Sara's user story)
    private void onShowTimeSeries() {
        SearchState state = viewModel.getState();
        if (state.getRows() == null || state.getRows().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No search results to analyze!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        List<NaturalEvent> currentEvents = new ArrayList<>();
        for (EventTableRow row : state.getRows()) {
            currentEvents.add(row.getRawEvent());
        }

        TimeSeriesView dialog = new TimeSeriesView(
                SwingUtilities.getWindowAncestor(this),
                currentEvents,
                timeSeriesController,
                timeSeriesViewModel
        );
        dialog.setVisible(true);
    }

    // function for opening the event detail dialog on a double-clicked row (User Story 2)
    private void onShowEventDetail() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        SearchState state = viewModel.getState();
        EventTableRow selectedEventRow = state.getRows().get(selectedRow);

        EventDetailView dialog = new EventDetailView(
                SwingUtilities.getWindowAncestor(this),
                selectedEventRow.getRawEvent(),
                viewEventDetailController,
                eventDetailViewModel
        );
        dialog.setVisible(true);
    }

    private void onAddToFavourite() {
        int selectedRow = resultsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an event first."
            );
            return;
        }

        SearchState state = viewModel.getState();

        EventTableRow selectedEventRow = state.getRows().get(selectedRow);

        addToFavouriteController.execute(selectedEventRow.getEventId());

        JOptionPane.showMessageDialog(
                this,
                addToFavouriteViewModel.getMessage()
        );
    }

    private void onSearch() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        String category = ALL_CATEGORIES.equals(selectedCategory) ? null : selectedCategory;

        EventFilter.StatusFilter statusFilter = mapStatus((String) statusComboBox.getSelectedItem());

        int days = (Integer) daysBackSpinner.getValue();
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days);

        int resultLimit = (Integer) resultLimitSpinner.getValue();

        statusLabel.setText("Searching...");
        controller.search(category, statusFilter, startDate, endDate, resultLimit);
    }

    private EventFilter.StatusFilter mapStatus(String selected) {
        if ("Active".equals(selected)) {
            return EventFilter.StatusFilter.ACTIVE;
        } else if ("Closed".equals(selected)) {
            return EventFilter.StatusFilter.CLOSED;
        }
        return EventFilter.StatusFilter.ALL;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (SearchViewModel.STATE_PROPERTY.equals(event.getPropertyName())) {
            render((SearchState) event.getNewValue());
        }
    }

    private void render(SearchState state) {
        tableModel.setRowCount(0);

        if (state.hasError()) {
            statusLabel.setText("Error: " + state.getErrorMessage());
            return;
        }

        for (EventTableRow row : state.getRows()) {
            tableModel.addRow(new Object[]{
                    row.getTitle(), row.getCategory(), row.getStatus(), row.getEventDate(), row.getCoordinates()
            });
        }
        statusLabel.setText(state.getRows().size() + " event(s) found.");
    }
}
