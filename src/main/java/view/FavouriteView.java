package view;

import interface_adapter.GetFavourite.GetFavouriteController;
import interface_adapter.GetFavourite.GetFavouritePresenter;
import interface_adapter.GetFavourite.GetFavouriteViewModel;

import javax.swing.*;
import java.awt.*;

public class FavouriteView extends JPanel {
    private final GetFavouriteController controller;
    private final GetFavouriteViewModel viewModel;

    private final DefaultListModel<String> listModel;
    private final JList<String> favouriteList;
    private final JLabel titleLabel;

    public FavouriteView(
            GetFavouriteController controller,
            GetFavouriteViewModel viewModel) {

        this.controller = controller;
        this.viewModel = viewModel;

        setLayout(new BorderLayout(10, 10));

        titleLabel = new JLabel(
                "My Favourites ❤️",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("SansSerif", Font.BOLD, 20)
        );

        listModel = new DefaultListModel<>();

        favouriteList = new JList<>(listModel);

        favouriteList.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(favouriteList);

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Refresh the favourite list displayed by the view.
     */
    public void updateView() {

        controller.execute();

        listModel.clear();

        for (String eventId :
                viewModel.getFavouriteEventIds()) {

            listModel.addElement(eventId);
        }
    }






























//    private final JList<String> favouriteList;
//    private final DefaultListModel<String> listModel;
//    private final JButton removeButton;
//
//    public FavouriteView() {
//
//        setLayout(new BorderLayout());
//
//        JLabel titleLabel =
//                new JLabel(
//                        "My Favourites ❤️",
//                        SwingConstants.CENTER
//                );
//
//        titleLabel.setFont(
//                new Font("SansSerif", Font.BOLD, 18)
//        );
//
//        listModel =
//                new DefaultListModel<>();
//
//        favouriteList =
//                new JList<>(listModel);
//
//        removeButton =
//                new JButton("Remove from Favourite");
//
//        add(
//                titleLabel,
//                BorderLayout.NORTH
//        );
//
//        add(
//                new JScrollPane(favouriteList),
//                BorderLayout.CENTER
//        );
//
//        add(
//                removeButton,
//                BorderLayout.SOUTH
//        );
//    }
//
//
//
//}
















    //    private JList<String> favouriteJList;
//    private DefaultListModel<String> listModel;
//    private JButton deleteButton;
//    private JButton addButton;
//
//    // Save the FavouriteList
//    private final FavouriteList favouriteList;
//
//    public FavouriteView(FavouriteList favouriteList) {
//        this.favouriteList = favouriteList;
//
//        setLayout(new BorderLayout());
//
//        JLabel titleLabel = new JLabel("My Favourite ❤️", SwingConstants.CENTER);
//        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
//        add(titleLabel, BorderLayout.NORTH);
//
//        listModel = new DefaultListModel<>();
//        favouriteJList = new JList<>(listModel);
//        add(new JScrollPane(favouriteJList), BorderLayout.CENTER);
//
//        JPanel bottomPanel = new JPanel();
//        deleteButton = new JButton("🗑️ Delete From My Favourite");
//        bottomPanel.add(deleteButton);
//        add(bottomPanel, BorderLayout.SOUTH);
//
//        deleteButton.addActionListener(e -> {
//            int selectedIndex = favouriteJList.getSelectedIndex();
//            if (selectedIndex != -1) {
//                // Remove
//                favouriteList.getNaturalEventList().remove(selectedIndex);
//                // Refresh
//                updateView();
//            } else {
//                JOptionPane.showMessageDialog(this, "Please select the event you want to delete!");
//            }
//        });
//
//        // Load the data
//        updateView();
//    }
//
//    // Reload the new UI by the newest data from Favourite List
//    public void updateView() {
//        listModel.clear();
//        for (NaturalEvent event : favouriteList.getNaturalEventList()) {
//            listModel.addElement(event.getTitle() + " (" + event.getEventDate() + ")");
//        }
//    }
}