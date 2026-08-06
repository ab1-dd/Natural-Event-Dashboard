package use_case.viewEventDetail;

public interface ViewEventDetailOutputBoundary {
    void prepareSuccessView(ViewEventDetailOutputData outputData);
    void prepareFailView(String error);
}
