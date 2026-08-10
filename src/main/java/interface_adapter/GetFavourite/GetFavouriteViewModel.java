package interface_adapter.GetFavourite;

import java.util.ArrayList;
import java.util.List;

public class GetFavouriteViewModel {
    private List<String> favouriteEventIds;

    public GetFavouriteViewModel() {
        this.favouriteEventIds = new ArrayList<>();
    }

    public List<String> getFavouriteEventIds() {
        return favouriteEventIds;
    }

    public void setFavouriteEventIds(List<String> favouriteEventIds) {
        this.favouriteEventIds = favouriteEventIds;
    }
}
