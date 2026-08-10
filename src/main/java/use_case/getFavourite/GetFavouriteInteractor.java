package use_case.getFavourite;

import entity.FavouriteList;
import entity.NaturalEvent;
import use_case.search_events.EventDataAccessInterface;
import use_case.search_events.EventFetchException;

import java.util.ArrayList;
import java.util.List;

public class GetFavouriteInteractor implements GetFavouriteInputBoundary{
    private final FavouriteList favouriteList;
    private final GetFavouriteOutputBoundary presenter;
    private final EventDataAccessInterface dataAccess;

    public GetFavouriteInteractor(
            FavouriteList favouriteList,
            GetFavouriteOutputBoundary presenter, EventDataAccessInterface dataAccess) {

        this.favouriteList = favouriteList;
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    @Override
    public void execute(GetFavouriteInputData inputData) {

        List<String> favouriteEventIds =
                new ArrayList<>(favouriteList.getNaturalEventList());

        List<List<String > > eventInfoList = new ArrayList<>();

        for (String eventID : favouriteEventIds) {
            try{
                NaturalEvent event = dataAccess.fetchEventById(eventID);
                List<String> info = List.of(event.getTitle(), event.getEventDate(), event.getSourceLinks());
                eventInfoList.add(info);
                GetFavouriteOutputData outputData =
                        new GetFavouriteOutputData(eventInfoList);
                presenter.present(outputData);
            }catch (EventFetchException ignored){

            }
        }
    }
}
