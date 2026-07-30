package entity;

import java.util.List;

public class PolygonEventCoordinates implements EventCoordinates{
    private Ring outterRing;
    private List<Ring> innerRing;
    private String date;

    public PolygonEventCoordinates(Ring outterRing, String date){
        this.outterRing = outterRing;
        this.innerRing = null;
        this.date = date;
    }

    public PolygonEventCoordinates(Ring outterRing, List<Ring> innerRing, String date){
        this.outterRing = outterRing;
        this.innerRing = innerRing;
        this.date = date;
    }



    public String getCoordinates(){
        return
    }
}
