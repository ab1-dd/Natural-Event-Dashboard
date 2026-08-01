package app;

import data_access.EonetEventDataAccessObject;
import interface_adapter.search_events.SearchController;
import interface_adapter.search_events.SearchPresenter;
import interface_adapter.search_events.SearchViewModel;
import use_case.search_events.EventDataAccessInterface;
import use_case.search_events.SearchEventsInputBoundary;
import use_case.search_events.SearchEventsInteractor;
import use_case.search_events.SearchEventsOutputBoundary;
import view.MainFrame;
import view.SearchView;

/**
 * Wires up every layer (data access -> use case -> interface adapters ->
 * view) into a runnable MainFrame. This is the only class that is allowed to
 * know about every layer at once.
 */
public class AppBuilder {

    public MainFrame build() {
        EventDataAccessInterface eventDataAccess = new EonetEventDataAccessObject();

        SearchViewModel searchViewModel = new SearchViewModel();
        SearchEventsOutputBoundary searchPresenter = new SearchPresenter(searchViewModel);
        SearchEventsInputBoundary searchInteractor = new SearchEventsInteractor(eventDataAccess, searchPresenter);
        SearchController searchController = new SearchController(searchInteractor);

        SearchView searchView = new SearchView(searchController, searchViewModel);

        return new MainFrame(searchView);
    }
}
