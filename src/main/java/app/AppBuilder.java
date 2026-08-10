package app;

import data_access.EonetEventDataAccessObject;
import entity.FavouriteList;
import interface_adapter.GetFavourite.GetFavouriteController;
import interface_adapter.GetFavourite.GetFavouritePresenter;
import interface_adapter.GetFavourite.GetFavouriteViewModel;
import interface_adapter.addToFavourite.AddToFavouritePresenter;
import interface_adapter.addToFavourite.AddToFavouriteViewModel;
import interface_adapter.generateChart.ChartViewModel;
import interface_adapter.generateChart.GenerateChartController;
import interface_adapter.generateChart.GenerateChartPresenter;
import interface_adapter.search_events.SearchController;
import interface_adapter.search_events.SearchPresenter;
import interface_adapter.search_events.SearchViewModel;
import interface_adapter.addToFavourite.AddToFavouriteController;
import interface_adapter.timeSeriesAnalytics.TimeSeriesController;
import interface_adapter.timeSeriesAnalytics.TimeSeriesPresenter;
import interface_adapter.timeSeriesAnalytics.TimeSeriesViewModel;
import interface_adapter.viewEventDetail.EventDetailViewModel;
import interface_adapter.viewEventDetail.ViewEventDetailController;
import interface_adapter.viewEventDetail.ViewEventDetailPresenter;
import use_case.getFavourite.GetFavouriteInputBoundary;
import use_case.getFavourite.GetFavouriteInteractor;
import use_case.getFavourite.GetFavouriteOutputBoundary;
import use_case.addToFavourite.AddToFavouriteInputBoundary;
import use_case.addToFavourite.AddToFavouriteInteractor;
import use_case.addToFavourite.AddToFavouriteOutputBoundary;
import use_case.generateChart.GenerateChartInputBoundary;
import use_case.generateChart.GenerateChartInteractor;
import use_case.generateChart.GenerateChartOutputBoundary;
import use_case.search_events.EventDataAccessInterface;
import use_case.search_events.SearchEventsInputBoundary;
import use_case.search_events.SearchEventsInteractor;
import use_case.search_events.SearchEventsOutputBoundary;
import use_case.timeSeriesAnalytics.TimeSeriesAnalyticsInputBoundary;
import use_case.timeSeriesAnalytics.TimeSeriesAnalyticsInteractor;
import use_case.timeSeriesAnalytics.TimeSeriesAnalyticsOutputBoundary;
import use_case.viewEventDetail.ViewEventDetailInputBoundary;
import use_case.viewEventDetail.ViewEventDetailInteractor;
import use_case.viewEventDetail.ViewEventDetailOutputBoundary;
import view.FavouriteView;
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

        // for favourite usecase
        AddToFavouriteViewModel addToFavouriteViewModel = new AddToFavouriteViewModel();
        FavouriteList favouriteList = new FavouriteList(); // create instance of favourite list
        AddToFavouriteOutputBoundary addToFavouritePresenter = new AddToFavouritePresenter(addToFavouriteViewModel);
        AddToFavouriteInputBoundary addToFavouriteInteractor = new AddToFavouriteInteractor(favouriteList, addToFavouritePresenter);
        AddToFavouriteController addToFavouriteController = new AddToFavouriteController(addToFavouriteInteractor);

        // for favourite tab
        GetFavouriteViewModel getFavouriteViewModel = new GetFavouriteViewModel();
        GetFavouriteOutputBoundary getFavouritePresenter = new GetFavouritePresenter(getFavouriteViewModel);
        GetFavouriteInputBoundary getFavouriteInputBoundary = new GetFavouriteInteractor(favouriteList, getFavouritePresenter);
        GetFavouriteController getFavouriteController = new GetFavouriteController(getFavouriteInputBoundary);

        // for frequency chart usecase
        ChartViewModel chartViewModel = new ChartViewModel();
        GenerateChartOutputBoundary chartPresenter = new GenerateChartPresenter(chartViewModel);
        GenerateChartInputBoundary chartInteractor = new GenerateChartInteractor(chartPresenter);
        GenerateChartController generateChartController = new GenerateChartController(chartInteractor);

        // for time-series analytics usecase (Sara's user story)
        TimeSeriesViewModel timeSeriesViewModel = new TimeSeriesViewModel();
        TimeSeriesAnalyticsOutputBoundary timeSeriesPresenter = new TimeSeriesPresenter(timeSeriesViewModel);
        TimeSeriesAnalyticsInputBoundary timeSeriesInteractor = new TimeSeriesAnalyticsInteractor(timeSeriesPresenter);
        TimeSeriesController timeSeriesController = new TimeSeriesController(timeSeriesInteractor);

        // for event detail view usecase (Daniel's user story)
        EventDetailViewModel eventDetailViewModel = new EventDetailViewModel();
        ViewEventDetailOutputBoundary eventDetailPresenter = new ViewEventDetailPresenter(eventDetailViewModel);
        ViewEventDetailInputBoundary eventDetailInteractor = new ViewEventDetailInteractor(eventDetailPresenter);
        ViewEventDetailController viewEventDetailController = new ViewEventDetailController(eventDetailInteractor);

        SearchView searchView = new SearchView(searchController, searchViewModel, addToFavouriteController, addToFavouriteViewModel, generateChartController,
                chartViewModel, timeSeriesController, timeSeriesViewModel, viewEventDetailController, eventDetailViewModel);
        FavouriteView favouriteView = new FavouriteView(getFavouriteController, getFavouriteViewModel);

        return new MainFrame(searchView, favouriteView);
    }
}
