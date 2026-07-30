package entity;

import java.util.List;

/**
 * This class represent a Favourite list that we can add Natural event in the list
 */
public class FavouriteList {
    List<NaturalEvent> naturalEventList;

    public FavouriteList(List<NaturalEvent> naturalEventList) {
        this.naturalEventList = naturalEventList;
    }

    public List<NaturalEvent> getNaturalEventList() {
        return naturalEventList;
    }

    public void addNaturalEvent(NaturalEvent naturalEvent){
        naturalEventList.add(naturalEvent);
    }
}
