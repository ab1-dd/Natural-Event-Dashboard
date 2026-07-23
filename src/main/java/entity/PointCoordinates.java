package entity;

public class PointCoordinates {
    private final double latitude;
    private final double longitude;

    public PointCoordinates(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
