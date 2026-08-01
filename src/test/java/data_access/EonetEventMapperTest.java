package data_access;

import entity.NaturalEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies EonetEventMapper against a real sample response captured from
 * GET <a href="https://eonet.gsfc.nasa.gov/api/v3/events?status=open&days=30&limit=3">...</a>
 * so the mapping logic is checked against the actual EONET v3 JSON shape,
 * not an assumed one.
 */
class EonetEventMapperTest {

    private static final String SAMPLE_RESPONSE = "{"
            + "\"title\": \"EONET Events\","
            + "\"events\": ["
            + "  {"
            + "    \"id\": \"EONET_21965\","
            + "    \"title\": \"Wildfire Camden McCarthy Overpass Fire, Camden, Georgia\","
            + "    \"closed\": null,"
            + "    \"categories\": [{\"id\": \"wildfires\", \"title\": \"Wildfires\"}],"
            + "    \"sources\": [{\"id\": \"IRWIN\", \"url\": \"https://irwin.doi.gov/observer/incidents/2026-GAGAS-042702\"}],"
            + "    \"geometry\": [{"
            + "      \"magnitudeValue\": 600.00,"
            + "      \"magnitudeUnit\": \"acres\","
            + "      \"date\": \"2026-07-30T00:14:00Z\","
            + "      \"type\": \"Point\","
            + "      \"coordinates\": [-81.6666667, 30.9780556]"
            + "    }]"
            + "  },"
            + "  {"
            + "    \"id\": \"EONET_CLOSED_EXAMPLE\","
            + "    \"title\": \"Closed example event\","
            + "    \"closed\": \"2026-07-01T00:00:00Z\","
            + "    \"categories\": [{\"id\": \"volcanoes\", \"title\": \"Volcanoes\"}],"
            + "    \"sources\": [],"
            + "    \"geometry\": [{"
            + "      \"date\": \"2026-06-30T00:00:00Z\","
            + "      \"type\": \"Polygon\","
            + "      \"coordinates\": [[[-1.0, 1.0], [-1.0, 2.0], [-2.0, 2.0], [-1.0, 1.0]]]"
            + "    }]"
            + "  }"
            + "]"
            + "}";

    @Test
    void mapsOpenPointEventFromRealSampleShape() {
        List<NaturalEvent> events = EonetEventMapper.mapEventsResponse(SAMPLE_RESPONSE);

        NaturalEvent wildfire = events.get(0);
        assertEquals("EONET_21965", wildfire.getEventId());
        assertEquals("Wildfire Camden McCarthy Overpass Fire, Camden, Georgia", wildfire.getTitle());
        assertEquals("wildfires", wildfire.getCategoryID());
        assertTrue(wildfire.isOpen());
        assertFalse(wildfire.isClosed());
        assertEquals("2026-07-30T00:14:00Z", wildfire.getEventDate());
        assertEquals(1, wildfire.getEventLocation().size());
        // longitude/latitude must not be swapped: EONET gives [lon, lat] = [-81.6666667, 30.9780556]
        assertEquals("30.978056° N, -81.666667° W", wildfire.getEventLocation().get(0).getCoordinates());
    }

    @Test
    void mapsClosedPolygonEvent() {
        List<NaturalEvent> events = EonetEventMapper.mapEventsResponse(SAMPLE_RESPONSE);

        NaturalEvent closedEvent = events.get(1);
        assertEquals("EONET_CLOSED_EXAMPLE", closedEvent.getEventId());
        assertFalse(closedEvent.isOpen());
        assertTrue(closedEvent.isClosed());
        assertEquals("volcanoes", closedEvent.getCategoryID());
        assertTrue(closedEvent.getEventLocation().get(0).getCoordinates().startsWith("Polygon starting at"));
    }

    @Test
    void returnsEmptyListWhenNoEventsField() {
        List<NaturalEvent> events = EonetEventMapper.mapEventsResponse("{\"title\": \"EONET Events\"}");
        assertTrue(events.isEmpty());
    }
}
