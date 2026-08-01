package entity;

/**
 * This class represents a single location for an event.
 */
public class EventLocation {
    private final String date;
    private final EventCoordinates coordinates; // This EventCoordinates is an interface
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

    public String getDate() {
        return date;
    }

    public String getMagnitudeUnit() {
        return magnitudeUnit;
    }

    public Double getMagnitudeValue() {
        return magnitudeValue;
    }
}
