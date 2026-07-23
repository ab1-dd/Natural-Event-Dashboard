package entity;

public class PointEventCoordinates implements EventCoordinates {
    private PointCoordinates pointCoordinates;
    private String date;


    public PointEventCoordinates(double latitude, double longitude, String date){
        this.pointCoordinates = new PointCoordinates(latitude, longitude);
        this.date = date;
    }

    public PointEventCoordinates(PointCoordinates pointCoordinates, String date){
        this.pointCoordinates = pointCoordinates;
        this.date = date;
    }

    public String getCoordinates(){
        return String.format("%f° N, %f° W", pointCoordinates.getLatitude(), pointCoordinates.getLongitude());
    }
}
