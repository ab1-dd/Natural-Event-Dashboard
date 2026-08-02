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

    public boolean addNaturalEvent(NaturalEvent naturalEvent){
        for (NaturalEvent existingEvent : naturalEventList) {
            if (existingEvent.getEventId().equals(naturalEvent.getEventId())) {
                return false; // 已存在，不重复添加，返回 false
            }
        }

        naturalEventList.add(naturalEvent);
        return true;
    }
}
