package view;

import entity.FavouriteList;
import entity.NaturalEvent;

import javax.swing.*;
import java.awt.*;

public class FavouriteView extends JPanel {
    private JList<String> favouriteJList;
    private DefaultListModel<String> listModel;
    private JButton deleteButton;

    // Save the FavouriteList
    private final FavouriteList favouriteList;

    public FavouriteView(FavouriteList favouriteList) {
        this.favouriteList = favouriteList;

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("My Favourite ❤️", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        favouriteJList = new JList<>(listModel);
        add(new JScrollPane(favouriteJList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        deleteButton = new JButton("🗑️ Delete From My Favourite");
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        deleteButton.addActionListener(e -> {
            int selectedIndex = favouriteJList.getSelectedIndex();
            if (selectedIndex != -1) {
                // Remove
                favouriteList.getNaturalEventList().remove(selectedIndex);
                // Refresh
                updateView();
            } else {
                JOptionPane.showMessageDialog(this, "Please select the event you want to delete!");
            }
        });

        // Load the data
        updateView();
    }

    // Reload the new UI by the newest data from Favourite List
    public void updateView() {
        listModel.clear();
        for (NaturalEvent event : favouriteList.getNaturalEventList()) {
            listModel.addElement(event.getTitle() + " (" + event.getEventDate() + ")");
        }
    }
}