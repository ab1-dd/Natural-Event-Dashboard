package interface_adapter.generateChart;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChartViewModel {
    public static final String STATE_PROPERTY = "chart_state";

    private ChartState state = new ChartState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ChartState getState() {
        return state;
    }

    public void setState(ChartState state) {
        ChartState oldState = this.state;
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