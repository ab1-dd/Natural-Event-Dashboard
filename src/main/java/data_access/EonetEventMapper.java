package data_access;

import data_access.json.JsonArray;
import data_access.json.JsonObject;
import entity.EventCoordinates;
import entity.EventLocation;
import entity.NaturalEvent;
import entity.PointCoordinates;
import entity.PointEventCoordinates;
import entity.PolygonEventCoordinates;
import entity.Ring;

import java.util.ArrayList;
import java.util.List;

/**
 * Functions that turn EONET's JSON event objects into our NaturalEvent
 * entities.
 */
public final class EonetEventMapper {

    private EonetEventMapper() {
    }

    /**
     * @param eventsResponseJson the body of a GET to /api/v3/events
     */
    public static List<NaturalEvent> mapEventsResponse(String eventsResponseJson) {
        JsonObject root = new JsonObject(eventsResponseJson);
        JsonArray eventsArray = root.optJSONArray("events");
        if (eventsArray == null) {
            return new ArrayList<>();
        }

        List<NaturalEvent> events = new ArrayList<>();
        for (int i = 0; i < eventsArray.length(); i++) {
            events.add(mapEvent(eventsArray.getJSONObject(i)));
        }
        return events;
    }

    static NaturalEvent mapEvent(JsonObject eventJson) {
        String id = eventJson.optString("id", null);
        String title = eventJson.optString("title", null);

        String categoryId = mapCategoryId(eventJson.optJSONArray("categories"));
        String sourceLinks = mapSourceLinks(eventJson.optJSONArray("sources"));
        boolean isClosed = !eventJson.isNull("closed");

        List<EventLocation> locations = mapLocations(eventJson.optJSONArray("geometry"));
        String eventDate = locations.isEmpty() ? null : locations.get(0).getDate();

        return new NaturalEvent(id, title, eventDate, categoryId, sourceLinks, isClosed, locations);
    }

    private static String mapCategoryId(JsonArray categoriesJson) {
        if (categoriesJson == null || categoriesJson.isEmpty()) {
            return null;
        }
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < categoriesJson.length(); i++) {
            ids.add(categoriesJson.getJSONObject(i).optString("id", ""));
        }
        return String.join(",", ids);
    }

    private static String mapSourceLinks(JsonArray sourcesJson) {
        if (sourcesJson == null || sourcesJson.isEmpty()) {
            return null;
        }
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < sourcesJson.length(); i++) {
            urls.add(sourcesJson.getJSONObject(i).optString("url", ""));
        }
        return String.join(", ", urls);
    }

    private static List<EventLocation> mapLocations(JsonArray geometryJson) {
        List<EventLocation> locations = new ArrayList<>();
        if (geometryJson == null) {
            return locations;
        }
        for (int i = 0; i < geometryJson.length(); i++) {
            locations.add(mapLocation(geometryJson.getJSONObject(i)));
        }
        return locations;
    }

    static EventLocation mapLocation(JsonObject geometryJson) {
        String date = geometryJson.optString("date", null);
        Double magnitudeValue = geometryJson.has("magnitudeValue") && !geometryJson.isNull("magnitudeValue")
                ? geometryJson.getDouble("magnitudeValue")
                : null;
        String magnitudeUnit = geometryJson.optString("magnitudeUnit", null);
        String type = geometryJson.optString("type", "Point");

        EventCoordinates coordinates = "Polygon".equalsIgnoreCase(type)
                ? mapPolygonCoordinates(geometryJson.getJSONArray("coordinates"), date)
                : mapPointCoordinates(geometryJson.getJSONArray("coordinates"), date);

        return new EventLocation(date, coordinates, magnitudeValue, magnitudeUnit);
    }

    /**
     * EONET points are GeoJSON [longitude, latitude] pairs.
     */
    private static PointEventCoordinates mapPointCoordinates(JsonArray coordinatesJson, String date) {
        double longitude = coordinatesJson.getDouble(0);
        double latitude = coordinatesJson.getDouble(1);
        return new PointEventCoordinates(latitude, longitude, date);
    }

    /**
     * EONET polygons are GeoJSON coordinate arrays.
     */
    private static PolygonEventCoordinates mapPolygonCoordinates(JsonArray ringsJson, String date) {
        Ring outerRing = mapRing(ringsJson.getJSONArray(0));

        if (ringsJson.length() <= 1) {
            return new PolygonEventCoordinates(outerRing, date);
        }

        List<Ring> innerRings = new ArrayList<>();
        for (int i = 1; i < ringsJson.length(); i++) {
            innerRings.add(mapRing(ringsJson.getJSONArray(i)));
        }
        return new PolygonEventCoordinates(outerRing, innerRings, date);
    }

    private static Ring mapRing(JsonArray pointsJson) {
        List<PointCoordinates> points = new ArrayList<>();
        for (int i = 0; i < pointsJson.length(); i++) {
            JsonArray pair = pointsJson.getJSONArray(i);
            double longitude = pair.getDouble(0);
            double latitude = pair.getDouble(1);
            points.add(new PointCoordinates(latitude, longitude));
        }
        return new Ring(points);
    }
}
