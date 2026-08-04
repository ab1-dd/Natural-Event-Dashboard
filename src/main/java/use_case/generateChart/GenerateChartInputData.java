package use_case.generateChart;

import entity.NaturalEvent;
import java.util.List;

public class GenerateChartInputData {
    private final List<NaturalEvent> events;
    private final int daysPerUnit;

    public GenerateChartInputData(List<NaturalEvent> events, int daysPerUnit) {
        this.events = events;
        this.daysPerUnit = daysPerUnit;
    }

    public List<NaturalEvent> getEvents() {
        return events;
    }

    public int getDaysPerUnit() {
        return daysPerUnit;
    }
}