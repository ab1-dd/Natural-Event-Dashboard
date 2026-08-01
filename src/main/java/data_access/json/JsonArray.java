package data_access.json;

import java.util.List;

/**
 * Thin, read-only wrapper around a parsed JSON array, giving the mapper a
 * small typed API instead of casting List&lt;Object&gt; everywhere.
 */
public final class JsonArray {
    private final List<Object> values;

    JsonArray(List<Object> values) {
        this.values = values;
    }

    public int length() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    @SuppressWarnings("unchecked")
    public JsonObject getJSONObject(int index) {
        return new JsonObject((java.util.Map<String, Object>) values.get(index));
    }

    @SuppressWarnings("unchecked")
    public JsonArray getJSONArray(int index) {
        return new JsonArray((List<Object>) values.get(index));
    }

    public double getDouble(int index) {
        return ((Number) values.get(index)).doubleValue();
    }
}
