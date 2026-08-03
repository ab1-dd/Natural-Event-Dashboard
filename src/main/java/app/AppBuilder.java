package app;

import data_access.EonetEventDataAccessObject;
import entity.FavouriteList;
import entity.NaturalEvent;
import interface_adapter.addToFavourite.AddToFavouritePresenter;
import interface_adapter.search_events.SearchController;
import interface_adapter.search_events.SearchPresenter;
import interface_adapter.search_events.SearchViewModel;
import interface_adapter.addToFavourite.AddToFavouriteController;
import use_case.addToFavourite.AddToFavouriteInputBoundary;
import use_case.addToFavourite.AddToFavouriteInteractor;
import use_case.addToFavourite.AddToFavouriteOutputBoundary;
import use_case.search_events.EventDataAccessInterface;
import use_case.search_events.SearchEventsInputBoundary;
import use_case.search_events.SearchEventsInteractor;
import use_case.search_events.SearchEventsOutputBoundary;
import view.FavouriteView;
import view.MainFrame;
import view.SearchView;

import java.util.ArrayList;
import java.util.List;

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

        List<NaturalEvent> emptyList = new ArrayList<>();
        FavouriteList favouriteList = new FavouriteList(emptyList); // create instance of favourite list
        AddToFavouriteOutputBoundary addToFavouritePresenter = new AddToFavouritePresenter();
        AddToFavouriteInputBoundary addToFavouriteInteractor = new AddToFavouriteInteractor(favouriteList, addToFavouritePresenter);
        AddToFavouriteController addToFavouriteController = new AddToFavouriteController(addToFavouriteInteractor);

        SearchView searchView = new SearchView(searchController, searchViewModel, addToFavouriteController);
        FavouriteView favouriteView = new FavouriteView(favouriteList);

        return new MainFrame(searchView, favouriteView);
    }
}
