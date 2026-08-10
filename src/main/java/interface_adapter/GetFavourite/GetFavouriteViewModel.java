package interface_adapter.GetFavourite;

import java.util.ArrayList;
import java.util.List;

public class GetFavouriteViewModel {
    private List<List<String>> favouriteEventInfoList;

    public GetFavouriteViewModel() {
        this.favouriteEventInfoList = new ArrayList<>();
    }

    public List<List<String>> getFavouriteEventInfoList() {
        return favouriteEventInfoList;
    }

    public void setFavouriteEventInfoList(List<List<String>> favouriteEventInfoList) {
        this.favouriteEventInfoList = favouriteEventInfoList;
    }
}
