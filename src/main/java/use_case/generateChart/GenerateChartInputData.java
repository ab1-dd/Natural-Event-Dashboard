package use_case.generateChart;

import java.util.Map;

public class GenerateChartInputData {
    private final Map<String, String> eventMap ;
    private final int daysPerUnit;

    public GenerateChartInputData(Map<String, String> eventMap, int daysPerUnit) {
        this.eventMap = eventMap;
        this.daysPerUnit = daysPerUnit;
    }

    public Map<String, String> getEventMap() {
        return eventMap;
    }

    public int getDaysPerUnit() {
        return daysPerUnit;
    }
}