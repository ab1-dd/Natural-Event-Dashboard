package entity;

import java.util.List;

/**
 * This class represent a different type of location: Polygon.
 * Which is made of multiple point coordinate so by linking those point, we have a polygon stands for the area
 * I call the polygon as ring
 * There are outter ring and inner ring, if there is any inner ring,
 * then the area between outter one and the inner one is where the event is happening.
 */
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

    @Override
    public String getCoordinates() {
        if (outterRing == null || outterRing.getPointCoordinatesList().isEmpty()) {
            return "Polygon (No coordinates)";
        }

        // take the first coordinate poit of outter ring as beginning
        PointCoordinates firstPoint = outterRing.getPointCoordinatesList().get(0);
        int totalPoints = outterRing.size();

        // combine readable texts, e.g.："Polygon starting at 45.000000° N, 75.000000° W (8 vertices)"
        if (innerRing != null && !innerRing.isEmpty()) {
            return String.format("Polygon starting at %.4f° N, %.4f° W (%d outer vertices, %d inner rings)",
                    firstPoint.getLatitude(), firstPoint.getLongitude(), totalPoints, innerRing.size());
        } else {
            return String.format("Polygon starting at %.4f° N, %.4f° W (%d vertices)",
                    firstPoint.getLatitude(), firstPoint.getLongitude(), totalPoints);
        }
    }
}
