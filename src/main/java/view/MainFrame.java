package view;

import javax.swing.*;

/**
 * Top-level application window. Wraps whatever the AppBuilder assembles for
 * the currently-implemented screens.
 */
public class MainFrame extends JFrame {

    public MainFrame(SearchView searchView, FavouriteView favouriteView) {
        super("Natural Event Watch Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("🔍 Search Events", searchView);
        tabbedPane.addTab("❤️ My Favourites", favouriteView);

        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 1) { // 1 stands for second Tab
                favouriteView.updateView();
            }
        });

        setContentPane(tabbedPane);
        setSize(950, 650);
        setLocationRelativeTo(null);
    }
}
