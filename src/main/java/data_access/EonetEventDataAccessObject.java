package data_access;

import entity.EventFilter;
import entity.NaturalEvent;
import use_case.search_events.EventDataAccessInterface;
import use_case.search_events.EventFetchException;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Talks to the live NASA EONET v3 and converts the response
 * into NaturalEvent entities. See https://eonet.gsfc.nasa.gov/docs/v3
 */
public class EonetEventDataAccessObject implements EventDataAccessInterface {

    private static final String EVENTS_URL = "https://eonet.gsfc.nasa.gov/api/v3/events";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    private final HttpClient httpClient;

    public EonetEventDataAccessObject() {
        this(HttpClient.newHttpClient());
    }

    public EonetEventDataAccessObject(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<NaturalEvent> fetchEvents(EventFilter filter) throws EventFetchException {
        URI requestUri = buildRequestUri(filter);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestUri)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new EventFetchException(
                        "EONET returned HTTP " + response.statusCode() + " for " + requestUri);
            }

            return EonetEventMapper.mapEventsResponse(response.body());
        } catch (IOException | InterruptedException exception) {
            if (exception instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new EventFetchException("Could not reach the EONET API: " + exception.getMessage(), exception);
        }
    }

    private URI buildRequestUri(EventFilter filter) {
        List<String> params = new ArrayList<>();

        if (filter.hasCategory()) {
            params.add("category=" + encode(filter.getCategory()));
        }
        if (filter.getStatusFilter() != null) {
            params.add("status=" + encode(filter.getStatusFilter().toQueryValue()));
        }
        if (filter.getStartDate() != null) {
            params.add("start=" + encode(filter.getStartDate().format(DATE_FORMAT)));
        }
        if (filter.getEndDate() != null) {
            params.add("end=" + encode(filter.getEndDate().format(DATE_FORMAT)));
        }
        if (filter.getResultLimit() != null) {
            params.add("limit=" + filter.getResultLimit());
        }

        String query = String.join("&", params);
        String url = query.isEmpty() ? EVENTS_URL : EVENTS_URL + "?" + query;
        return URI.create(url);
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
