package interface_adapter.search_events;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Holds the current SearchState and notifies the view whenever it changes.
 * The presenter writes to this; the view only reads from it and listens.
 */
public class SearchViewModel {
    public static final String STATE_PROPERTY = "state";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private SearchState state = new SearchState();

    public SearchState getState() {
        return state;
    }

    public void setState(SearchState state) {
        SearchState oldState = this.state;
        this.state = state;
        support.firePropertyChange(STATE_PROPERTY, oldState, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
