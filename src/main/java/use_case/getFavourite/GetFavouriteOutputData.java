package use_case.getFavourite;

import java.util.List;

public class GetFavouriteOutputData {
    private final List<List<String> > favouriteEventInfo;

    public GetFavouriteOutputData(List<List<String> > favouriteEventInfo) {
        this.favouriteEventInfo = favouriteEventInfo;
    }

    public List<List<String> > getFavouriteEventInfo() {
        return favouriteEventInfo;
    }
}
