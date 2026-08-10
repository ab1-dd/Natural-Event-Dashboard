package use_case.getFavourite;

import entity.FavouriteList;

import java.util.ArrayList;
import java.util.List;

public class GetFavouriteInteractor implements GetFavouriteInputBoundary{
    private final FavouriteList favouriteList;
    private final GetFavouriteOutputBoundary presenter;

    public GetFavouriteInteractor(
            FavouriteList favouriteList,
            GetFavouriteOutputBoundary presenter) {

        this.favouriteList = favouriteList;
        this.presenter = presenter;
    }

    @Override
    public void execute(GetFavouriteInputData inputData) {

        List<String> favouriteEventIds =
                new ArrayList<>(favouriteList.getNaturalEventList());

        GetFavouriteOutputData outputData =
                new GetFavouriteOutputData(favouriteEventIds);

        presenter.present(outputData);
    }
}
