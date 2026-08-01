package data_access.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A small, dependency-free recursive-descent JSON parser. It only needs to
 * handle the shapes EONET actually returns (objects, arrays, strings,
 * numbers, booleans, null), so it deliberately isn't a general-purpose JSON
 * library.
 *
 * Parses into plain Java types:
 *   object -> LinkedHashMap&lt;String, Object&gt;
 *   array  -> ArrayList&lt;Object&gt;
 *   string -> String
 *   number -> Double
 *   true/false -> Boolean
 *   null -> null
 */
public final class JsonParser {

    private final String text;
    private int pos;

    private JsonParser(String text) {
        this.text = text;
        this.pos = 0;
    }

    public static Object parse(String text) {
        JsonParser parser = new JsonParser(text);
        parser.skipWhitespace();
        Object value = parser.parseValue();
        parser.skipWhitespace();
        return value;
    }

    private Object parseValue() {
        skipWhitespace();
        char c = peek();
        switch (c) {
            case '{':
                return parseObject();
            case '[':
                return parseArray();
            case '"':
                return parseString();
            case 't':
            case 'f':
                return parseBoolean();
            case 'n':
                return parseNull();
            default:
                return parseNumber();
        }
    }

    private Map<String, Object> parseObject() {
        Map<String, Object> result = new LinkedHashMap<>();
        expect('{');
        skipWhitespace();
        if (peek() == '}') {
            pos++;
            return result;
        }
        while (true) {
            skipWhitespace();
            String key = parseString();
            skipWhitespace();
            expect(':');
            Object value = parseValue();
            result.put(key, value);
            skipWhitespace();
            char next = peek();
            if (next == ',') {
                pos++;
            } else if (next == '}') {
                pos++;
                break;
            } else {
                throw syntaxError("Expected ',' or '}' in object");
            }
        }
        return result;
    }

    private List<Object> parseArray() {
        List<Object> result = new ArrayList<>();
        expect('[');
        skipWhitespace();
        if (peek() == ']') {
            pos++;
            return result;
        }
        while (true) {
            Object value = parseValue();
            result.add(value);
            skipWhitespace();
            char next = peek();
            if (next == ',') {
                pos++;
            } else if (next == ']') {
                pos++;
                break;
            } else {
                throw syntaxError("Expected ',' or ']' in array");
            }
        }
        return result;
    }

    private String parseString() {
        expect('"');
        StringBuilder builder = new StringBuilder();
        while (true) {
            char c = text.charAt(pos++);
            if (c == '"') {
                break;
            }
            if (c == '\\') {
                char escaped = text.charAt(pos++);
                switch (escaped) {
                    case '"':
                        builder.append('"');
                        break;
                    case '\\':
                        builder.append('\\');
                        break;
                    case '/':
                        builder.append('/');
                        break;
                    case 'b':
                        builder.append('\b');
                        break;
                    case 'f':
                        builder.append('\f');
                        break;
                    case 'n':
                        builder.append('\n');
                        break;
                    case 'r':
                        builder.append('\r');
                        break;
                    case 't':
                        builder.append('\t');
                        break;
                    case 'u':
                        String hex = text.substring(pos, pos + 4);
                        builder.append((char) Integer.parseInt(hex, 16));
                        pos += 4;
                        break;
                    default:
                        throw syntaxError("Unknown escape sequence: \\" + escaped);
                }
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    private Boolean parseBoolean() {
        if (text.startsWith("true", pos)) {
            pos += 4;
            return Boolean.TRUE;
        }
        if (text.startsWith("false", pos)) {
            pos += 5;
            return Boolean.FALSE;
        }
        throw syntaxError("Expected 'true' or 'false'");
    }

    private Object parseNull() {
        if (text.startsWith("null", pos)) {
            pos += 4;
            return null;
        }
        throw syntaxError("Expected 'null'");
    }

    private Double parseNumber() {
        int start = pos;
        if (peek() == '-') {
            pos++;
        }
        while (pos < text.length() && Character.isDigit(text.charAt(pos))) {
            pos++;
        }
        if (pos < text.length() && text.charAt(pos) == '.') {
            pos++;
            while (pos < text.length() && Character.isDigit(text.charAt(pos))) {
                pos++;
            }
        }
        if (pos < text.length() && (text.charAt(pos) == 'e' || text.charAt(pos) == 'E')) {
            pos++;
            if (pos < text.length() && (text.charAt(pos) == '+' || text.charAt(pos) == '-')) {
                pos++;
            }
            while (pos < text.length() && Character.isDigit(text.charAt(pos))) {
                pos++;
            }
        }
        if (pos == start) {
            throw syntaxError("Expected a number");
        }
        return Double.parseDouble(text.substring(start, pos));
    }

    private char peek() {
        skipWhitespace();
        if (pos >= text.length()) {
            throw syntaxError("Unexpected end of input");
        }
        return text.charAt(pos);
    }

    private void expect(char expected) {
        skipWhitespace();
        if (pos >= text.length() || text.charAt(pos) != expected) {
            throw syntaxError("Expected '" + expected + "'");
        }
        pos++;
    }

    private void skipWhitespace() {
        while (pos < text.length() && Character.isWhitespace(text.charAt(pos))) {
            pos++;
        }
    }

    private RuntimeException syntaxError(String message) {
        return new IllegalArgumentException(message + " at position " + pos);
    }
}
