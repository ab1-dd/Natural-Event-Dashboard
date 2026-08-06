package interface_adapter.timeSeriesAnalytics;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TimeSeriesViewModel {
    public static final String STATE_PROPERTY = "time_series_state";

    private TimeSeriesState state = new TimeSeriesState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public TimeSeriesState getState() {
        return state;
    }

    public void setState(TimeSeriesState state) {
        TimeSeriesState oldState = this.state;
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
