package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a Favourite list that we can add Natural event in the list
 */
public class FavouriteList {
    private List<String> naturalEventIDList = new ArrayList<>();

    public FavouriteList() {
        this.naturalEventIDList = new ArrayList<>();
    }

    public List<String> getNaturalEventList() {
        return naturalEventIDList;
    }

    public void addNaturalEvent(String naturalEventID){
        naturalEventIDList.add(naturalEventID);
    }

    public void remove(String naturalEventID){
        naturalEventIDList.remove(naturalEventID);
    }

    public boolean contain(String naturalEventID){
        for (String existingEventID : naturalEventIDList) {
            if (existingEventID.equals(naturalEventID)) {
                return true;
            }
        }
        return false;
    }
}
