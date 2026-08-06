package use_case.viewEventDetail;

import entity.NaturalEvent;

public class ViewEventDetailInputData {

    private final NaturalEvent event;

    public ViewEventDetailInputData(NaturalEvent event) {
        this.event = event;
    }

    public NaturalEvent getEvent() {
        return event;
    }
}
