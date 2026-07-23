package entity;

public class EventLocation {
    private final String date;
    private final EventCoordinates coordinates; // 这里的 EventCoordinates 可以是接口！
    private final Double magnitudeValue;
    private final String magnitudeUnit;

    public EventLocation(String date, EventCoordinates coordinates, Double magnitudeValue, String magnitudeUnit) {
        this.date = date;
        this.coordinates = coordinates;
        this.magnitudeValue = magnitudeValue;
        this.magnitudeUnit = magnitudeUnit;
    }

    public String getCoordinates() {
        return coordinates.getCoordinates();
    }
}