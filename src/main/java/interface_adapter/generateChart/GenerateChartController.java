package interface_adapter.generateChart;


import entity.NaturalEvent;
import use_case.generateChart.GenerateChartInputBoundary;
import use_case.generateChart.GenerateChartInputData;

import java.util.List;

public class GenerateChartController {

    private final GenerateChartInputBoundary interactor;

    public GenerateChartController(GenerateChartInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void generateChart(List<NaturalEvent> events, int daysPerUnit) {
        GenerateChartInputData inputData = new GenerateChartInputData(events, daysPerUnit);
        interactor.execute(inputData);
    }
}