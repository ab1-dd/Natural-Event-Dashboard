package entity;

public class EventLocationPoint implements EventLocationInterface{
    private final double latitude;
    private final double longitude;

    public EventLocationPoint(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocation(){
        return String.format("%f° N, %f° W", latitude, longitude);
    }

}
