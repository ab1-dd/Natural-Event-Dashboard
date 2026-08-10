package interface_adapter.generateChart;


import entity.NaturalEvent;
import use_case.generateChart.GenerateChartInputBoundary;
import use_case.generateChart.GenerateChartInputData;

import java.util.List;
import java.util.Map;

public class GenerateChartController {

    private final GenerateChartInputBoundary interactor;

    public GenerateChartController(GenerateChartInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void generateChart(Map<String, String> eventMap, int daysPerUnit) {
        GenerateChartInputData inputData = new GenerateChartInputData(eventMap, daysPerUnit);
        interactor.execute(inputData);
    }
}