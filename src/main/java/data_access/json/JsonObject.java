package data_access.json;

import java.util.Map;

/**
 * Thin, read-only wrapper around a parsed JSON object, mirroring the handful
 * of accessor methods the EONET mapper needs (optString, optJSONArray, etc.)
 * so the mapper reads naturally without hand-casting Maps everywhere.
 */
public final class JsonObject {
    private final Map<String, Object> values;

    @SuppressWarnings("unchecked")
    public JsonObject(String json) {
        this.values = (Map<String, Object>) JsonParser.parse(json);
    }

    JsonObject(Map<String, Object> values) {
        this.values = values;
    }

    public boolean has(String key) {
        return values.containsKey(key);
    }

    public boolean isNull(String key) {
        return !values.containsKey(key) || values.get(key) == null;
    }

    public String optString(String key) {
        return optString(key, null);
    }

    public String optString(String key, String defaultValue) {
        Object value = values.get(key);
        return value == null ? defaultValue : String.valueOf(value);
    }

    public double getDouble(String key) {
        return ((Number) values.get(key)).doubleValue();
    }

    @SuppressWarnings("unchecked")
    public JsonArray optJSONArray(String key) {
        Object value = values.get(key);
        return value == null ? null : new JsonArray((java.util.List<Object>) value);
    }

    public JsonArray getJSONArray(String key) {
        JsonArray array = optJSONArray(key);
        if (array == null) {
            throw new IllegalArgumentException("Missing required JSON array field: " + key);
        }
        return array;
    }
}
