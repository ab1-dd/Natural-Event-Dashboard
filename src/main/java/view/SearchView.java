package view;

import entity.EventFilter;
import interface_adapter.search_events.EventTableRow;
import interface_adapter.search_events.SearchController;
import interface_adapter.search_events.SearchState;
import interface_adapter.search_events.SearchViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

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
            ALL_CATEGORIES, "wildfires", "severeStorms", "volcanoes", "drought",
            "earthquakes", "floods", "landslides", "seaLakeIce", "snow", "tempExtremes"
    };

    private static final String[] STATUS_OPTIONS = {"Active", "Closed", "All"};

    private final SearchController controller;
    private final SearchViewModel viewModel;

    private final JComboBox<String> categoryComboBox = new JComboBox<>(CATEGORY_OPTIONS);
    private final JComboBox<String> statusComboBox = new JComboBox<>(STATUS_OPTIONS);
    private final JSpinner daysBackSpinner = new JSpinner(new SpinnerNumberModel(30, 1, 3650, 1));
    private final JSpinner resultLimitSpinner = new JSpinner(new SpinnerNumberModel(50, 1, 500, 1));
    private final JButton searchButton = new JButton("Search");
    private final JLabel statusLabel = new JLabel(" ");

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Title", "Category", "Status", "Event Date", "Coordinates"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable resultsTable = new JTable(tableModel);

    public SearchView(SearchController controller, SearchViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFilterPanel(), BorderLayout.NORTH);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        searchButton.addActionListener(event -> onSearch());

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
