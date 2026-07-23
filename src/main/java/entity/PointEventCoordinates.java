package entity;

public class PointEventCoordinates {
    private final double latitude;
    private final double longitude;


    public PointEventCoordinates(double latitude, double longitude, String date){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocation(){
        return String.format("%f° N, %f° W", latitude, longitude);
    }
}
