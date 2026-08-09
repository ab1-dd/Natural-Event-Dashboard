package entity;

import java.util.List;

/**
 * This class represent a Favourite list that we can add Natural event in the list
 */
public class FavouriteList {
    private List<String> naturalEventIDList;

    public FavouriteList(List<String> naturalEventIDListntList) {
        this.naturalEventIDList = naturalEventIDList;
    }

    public List<String> getNaturalEventList() {
        return naturalEventIDList;
    }

    /**
     * @param naturalEventID a natural event's ID.
     * @return a boolean type, false if naturalEvent already exist in favourite list, return true otherwise.
     */
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
