package view;

import entity.NaturalEvent;
import interface_adapter.viewEventDetail.EventDetailState;
import interface_adapter.viewEventDetail.EventDetailViewModel;
import interface_adapter.viewEventDetail.ViewEventDetailController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;

/**
 * Shows the full detail of a single natural event: title, category, status,
 * event date, coordinates, and source link.
 */
public class EventDetailView extends JDialog implements PropertyChangeListener {

    private final ViewEventDetailController controller;
    private final EventDetailViewModel viewModel;

    private final JLabel titleValue = new JLabel();
    private final JLabel categoryValue = new JLabel();
    private final JLabel statusValue = new JLabel();
    private final JLabel eventDateValue = new JLabel();
    private final JLabel coordinatesValue = new JLabel();
    private final JTextField sourceLinkField = new JTextField();
    private final JButton openLinkButton = new JButton("Open Link");
    private final JLabel errorLabel = new JLabel(" ");

    public EventDetailView(Window owner, NaturalEvent event, ViewEventDetailController controller,
                            EventDetailViewModel viewModel) {
        super(owner, "Event Details", ModalityType.MODELESS);
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setSize(480, 320);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(8, 8));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(buildDetailPanel(), BorderLayout.CENTER);
        add(errorLabel, BorderLayout.SOUTH);

        sourceLinkField.setEditable(false);
        openLinkButton.addActionListener(e -> openSourceLink());

        controller.viewDetail(event);
        render(viewModel.getState());
    }

    private JPanel buildDetailPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        row = addRow(panel, gbc, row, "Title:", titleValue);
        row = addRow(panel, gbc, row, "Category:", categoryValue);
        row = addRow(panel, gbc, row, "Status:", statusValue);
        row = addRow(panel, gbc, row, "Event Date:", eventDateValue);
        row = addRow(panel, gbc, row, "Coordinates:", coordinatesValue);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Source Link:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(sourceLinkField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(openLinkButton, gbc);

        return panel;
    }

    private int addRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JLabel valueLabel) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(valueLabel, gbc);

        return row + 1;
    }

    private void openSourceLink() {
        String link = sourceLinkField.getText();
        if (link == null || !(link.startsWith("http://") || link.startsWith("https://"))) {
            JOptionPane.showMessageDialog(this, "No valid source link to open.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not open link: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (EventDetailViewModel.STATE_PROPERTY.equals(evt.getPropertyName())) {
            render(viewModel.getState());
        }
    }

    private void render(EventDetailState state) {
        if (state.hasError()) {
            errorLabel.setText("Error: " + state.getErrorMessage());
            openLinkButton.setEnabled(false);
            return;
        }

        titleValue.setText(state.getTitle());
        categoryValue.setText(state.getCategory());
        statusValue.setText(state.getStatus());
        eventDateValue.setText(state.getEventDate());
        coordinatesValue.setText(state.getCoordinates());
        sourceLinkField.setText(state.getSourceLink());
        openLinkButton.setEnabled(state.getSourceLink() != null && state.getSourceLink().startsWith("http"));
        errorLabel.setText(" ");
    }
}
