package view;

import interface_adapter.GetFavourite.GetFavouriteController;
import interface_adapter.GetFavourite.GetFavouriteViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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

        for (List<String> eventInfo :
                viewModel.getFavouriteEventInfoList()) {

            listModel.addElement(eventInfo.get(0) + "; " + eventInfo.get(1) + "; " + eventInfo.get(2));
        }
    }
}
