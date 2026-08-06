package interface_adapter.viewEventDetail;

import entity.NaturalEvent;
import use_case.viewEventDetail.ViewEventDetailInputBoundary;
import use_case.viewEventDetail.ViewEventDetailInputData;

public class ViewEventDetailController {

    private final ViewEventDetailInputBoundary interactor;

    public ViewEventDetailController(ViewEventDetailInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void viewDetail(NaturalEvent event) {
        interactor.execute(new ViewEventDetailInputData(event));
    }
}
