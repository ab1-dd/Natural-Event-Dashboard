package use_case.GetFavourite;

import java.util.List;

public class GetFavouriteOutputData {
    private final List<String> favouriteEventIds;

    public GetFavouriteOutputData(List<String> favouriteEventIds) {
        this.favouriteEventIds = favouriteEventIds;
    }

    public List<String> getFavouriteEventIds() {
        return favouriteEventIds;
    }
}
