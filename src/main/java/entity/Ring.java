package entity;

import java.util.List;

public class Ring {
    private final List<PointCoordinates> pointCoordinatesList;

    public Ring(List<PointCoordinates> pointCoordinatesList){
        this.pointCoordinatesList = pointCoordinatesList;
    }

    public List<PointCoordinates> getPointCoordinatesList() {
        return pointCoordinatesList;
    }

    public int size() {
        return pointCoordinatesList != null ? pointCoordinatesList.size() : 0;
    }
}
