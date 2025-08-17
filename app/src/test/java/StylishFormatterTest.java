import hexlet.code.Formatter;
import hexlet.code.formatters.StylishFormatter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StylishFormatterTest {
    private static final int N1 = 1;
    private static final int N2 = 2;
    private static final int N3 = 3;
    private static final int N4 = 4;
    private static final int N5 = 5;
    private static final int TIMEOUT50 = 50;

    @Test
    void mixed() {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(N1, "2");
        map.put(N3, "4");

        List<Map<String, Object>> diff = List.of(
                Map.of("key", "host", "status", "unchanged", "oldValue", "hexlet.io"),
                Map.of("key", "proxy", "status", "changed", "oldValue", "123.123.12.13", "newValue", "122.121.10.11"),
                Map.of("key", "timeout", "status", "removed", "oldValue", TIMEOUT50),
                Map.of("key", "data", "status", "added", "newValue", List.of(N1, N2, N3, N4, N5)),
                Map.of("key", "value", "status", "removed", "oldValue", map),
                Map.of("key", "verbose", "status", "added", "newValue", true)
        );

        Formatter formatter = new StylishFormatter();
        String actual = formatter.format(diff);

        String expected =
                "{\n"
                + "    host: hexlet.io\n"
                + "  - proxy: 123.123.12.13\n"
                + "  + proxy: 122.121.10.11\n"
                + "  - timeout: 50\n"
                + "  + data: [1, 2, 3, 4, 5]\n"
                + "  - value: {1=2, 3=4}\n"
                + "  + verbose: true\n"
                + "}";

        assertEquals(expected, actual);
    }

    @Test
    void empty() {
        assertEquals("{\n}", new StylishFormatter().format(List.of()));
    }

    @Test
    void onlyAdded() {
        List<Map<String, Object>> diff = List.of(
                Map.of("key", "c", "status", "added", "newValue", List.of(N1, N2)));

        String expected =
                "{\n"
                + "  + c: [1, 2]\n"
                + "}";

        assertEquals(expected, new StylishFormatter().format(diff));
    }

    @Test
    void onlyRemoved() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "b");
        map.put("status", "removed");
        map.put("oldValue", null);

        List<Map<String, Object>> diff = List.of(map);

        String expected =
                "{\n"
                + "  - b: null\n"
                + "}";

        assertEquals(expected, new StylishFormatter().format(diff));
    }

    @Test
    void onlyChanged() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("key", "x");
        map1.put("status", "changed");
        map1.put("oldValue", null);
        map1.put("newValue", N5);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("key", "y");
        map2.put("status", "changed");
        map2.put("oldValue", true);
        map2.put("newValue", null);


        List<Map<String, Object>> diff = List.of(map1, map2);

        String expected =
                "{\n"
                + "  - x: null\n"
                + "  + x: 5\n"
                + "  - y: true\n"
                + "  + y: null\n"
                + "}";

        assertEquals(expected, new StylishFormatter().format(diff));
    }

    @Test
    void onlyUnchanged() {
        List<Map<String, Object>> diff = List.of(
                Map.of("key", "a", "status", "unchanged", "oldValue", N4));

        String expected =
                "{\n"
                + "    a: 4\n"
                + "}";

        assertEquals(expected, new StylishFormatter().format(diff));
    }
}
