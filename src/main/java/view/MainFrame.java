package view;

import javax.swing.*;

/**
 * Top-level application window. Wraps whatever the AppBuilder assembles for
 * the currently-implemented screens.
 */
public class MainFrame extends JFrame {

    public MainFrame(SearchView searchView) {
        super("Natural Event Watch Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(searchView);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }
}
