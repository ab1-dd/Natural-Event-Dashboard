package interface_adapter.viewEventDetail;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EventDetailViewModel {
    public static final String STATE_PROPERTY = "event_detail_state";

    private EventDetailState state = new EventDetailState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public EventDetailState getState() {
        return state;
    }

    public void setState(EventDetailState state) {
        EventDetailState oldState = this.state;
        this.state = state;
        support.firePropertyChange(STATE_PROPERTY, oldState, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
