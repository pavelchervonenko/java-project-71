import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import hexlet.code.Formatter;
import hexlet.code.formatters.JsonFormatter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class JsonFormatterTest {
    private static final int N1 = 1;
    private static final int N2 = 2;
    private static final int N3 = 3;
    private static final int N4 = 4;
    private static final int N5 = 5;

    private final Formatter formatter = new JsonFormatter();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void mixed() throws Exception {
        Map<Integer, String> inner = new LinkedHashMap<>();
        inner.put(N1, "2");
        inner.put(N3, "4");

        List<Map<String, Object>> diff = List.of(
                entryUnchanged("host", "hexlet.io"),
                entryChanged("proxy", "123.123.12.13", "122.121.10.11"),
                entryRemoved("timeout", N5),
                entryAdded("data", List.of(N1, N2, N3, N4, N5)),
                entryRemoved("value", inner),
                entryAdded("verbose", true)
        );

        String actual = formatter.format(diff);
        JsonNode actualNode = mapper.readTree(actual);

        ArrayNode expected = mapper.createArrayNode();
        expected.add(obj("host", "unchanged", "hexlet.io", null));
        expected.add(obj("proxy", "changed", "123.123.12.13", "122.121.10.11"));
        expected.add(obj("timeout", "removed", N5, null));
        expected.add(obj("data", "added", null, List.of(N1, N2, N3, N4, N5)));
        expected.add(obj("value", "removed", inner, null));
        expected.add(obj("verbose", "added", null, true));

        assertEquals(expected, actualNode);
    }

    @Test
    void empty() throws Exception {
        String actual = formatter.format(List.of());
        JsonNode actualNode = mapper.readTree(actual);

        ArrayNode expected = mapper.createArrayNode();

        assertEquals(expected, actualNode);
    }

    @Test
    void addedRemovedChanged() throws Exception {
        List<Map<String, Object>> diff = List.of(
                entryAdded("age", N4),
                entryRemoved("name", "Pavel"),
                entryChanged("active", false, true),
                entryChanged("note", null, "hi")
        );

        String actual = formatter.format(diff);
        JsonNode actualNode = mapper.readTree(actual);

        ArrayNode expected = mapper.createArrayNode();

        expected.add(obj("age", "added", null, N4));
        expected.add(obj("name", "removed", "Pavel", null));
        expected.add(obj("active", "changed", false, true));
        expected.add(obj("note", "changed", null, "hi"));

        assertEquals(expected, actualNode);
    }

    @Test
    void addedChangedWithListAndMap() throws Exception {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("k", "v");
        List<Integer> list = List.of(N1, N2, N3);

        List<Map<String, Object>> diff = List.of(
                entryAdded("cfg", map),
                entryAdded("arr", list),
                entryChanged("data", map, false),
                entryChanged("arr2", list, map)
        );

        String actual = formatter.format(diff);
        JsonNode actualNode = mapper.readTree(actual);

        ArrayNode expected = mapper.createArrayNode();
        expected.add(obj("cfg", "added", null, map));
        expected.add(obj("arr", "added", null, list));
        expected.add(obj("data", "changed", map, false));
        expected.add(obj("arr2", "changed", list, map));

        assertEquals(expected, actualNode);
    }

    private static Map<String, Object> entryAdded(String key, Object newValue) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("key", key);
        m.put("status", "added");
        m.put("newValue", newValue);
        return m;
    }

    private static Map<String, Object> entryRemoved(String key, Object oldValue) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("key", key);
        m.put("status", "removed");
        m.put("oldValue", oldValue);
        return m;
    }

    private static Map<String, Object> entryChanged(String key, Object oldValue, Object newValue) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("key", key);
        m.put("status", "changed");
        m.put("oldValue", oldValue);
        m.put("newValue", newValue);
        return m;
    }

    private static Map<String, Object> entryUnchanged(String key, Object oldValue) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("key", key);
        m.put("status", "unchanged");
        m.put("oldValue", oldValue);
        return m;
    }

    private ObjectNode obj(String key, String status, Object oldValue, Object newValue) {
        ObjectNode node = mapper.createObjectNode();
        node.put("key", key);
        node.put("status", status);

        if (oldValueWasProvided(status)) {
            node.set("oldValue", mapper.valueToTree(oldValue));
        }
        if (newValueWasProvided(status)) {
            node.set("newValue", mapper.valueToTree(newValue));
        }
        return node;
    }

    private static boolean oldValueWasProvided(String status) {
        return switch (status) {
            case "removed", "unchanged", "changed" -> true;
            default -> false;
        };
    }

    private static boolean newValueWasProvided(String status) {
        return switch (status) {
            case "added", "changed" -> true;
            default -> false;
        };
    }

}
