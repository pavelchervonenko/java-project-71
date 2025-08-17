import hexlet.code.Formatter;
import hexlet.code.formatters.PlainFormatter;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlainFormatterTest {
    private static final int N1 = 1;
    private static final int N2 = 2;
    private static final int N3 = 3;
    private static final int N4 = 4;
    private static final int N5 = 5;


    @Test
    void mixed() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("k", "v");

        List<Integer> list = List.of(N1, N2, N3);

        List<Map<String, Object>> diff = List.of(
                Map.of("key", "cfg", "status", "added", "newValue", map),
                Map.of("key", "data", "status", "added", "newValue", list),
                Map.of("key", "host", "status", "changed", "oldValue", map, "newValue", list),
                Map.of("key", "host2", "status", "changed", "oldValue", list, "newValue", false),
                Map.of("key", "value", "status", "removed", "oldValue", map)
        );

        Formatter formatter = new PlainFormatter();
        String actual = formatter.format(diff);

        String expected =
                "Property 'cfg' was added with value: [complex value]\n"
                + "Property 'data' was added with value: [complex value]\n"
                + "Property 'host' was updated. From [complex value] to [complex value]\n"
                + "Property 'host2' was updated. From [complex value] to false\n"
                + "Property 'value' was removed";

        assertEquals(expected, actual);
    }

    @Test
    void empty() {
        assertEquals("", new PlainFormatter().format(List.of()));
    }

    @Test
    void onlyAdded() {
        List<Map<String, Object>> diff = List.of(
                Map.of("key", "a", "status", "added", "newValue", List.of(N1, N2)),
                Map.of("key", "b", "status", "added", "newValue", N5));


        String expected =
                "Property 'a' was added with value: [complex value]\n"
                + "Property 'b' was added with value: 5";

        assertEquals(expected, new PlainFormatter().format(diff));
    }

    @Test
    void onlyRemoved() {
        List<Map<String, Object>> diff = List.of(
                Map.of("key", "c", "status", "removed", "oldValue", List.of(N3, N4)),
                Map.of("key", "d", "status", "removed", "oldValue", "pavel"));


        String expected =
                "Property 'c' was removed\n"
                + "Property 'd' was removed";

        assertEquals(expected, new PlainFormatter().format(diff));
    }

    @Test
    void onlyChanged() {
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("key", "x");
        map1.put("status", "changed");
        map1.put("oldValue", null);
        map1.put("newValue", N5);

        Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("key", "y");
        map2.put("status", "changed");
        map2.put("oldValue", true);
        map2.put("newValue", null);

        List<Map<String, Object>> diff = List.of(map1, map2);

        String expected =
                "Property 'x' was updated. From null to 5\n"
                + "Property 'y' was updated. From true to null";

        assertEquals(expected, new PlainFormatter().format(diff));
    }

    @Test
    void onlyUnchanged() {
        List<Map<String, Object>> diff = List.of(
                Map.of("key", "a", "status", "unchanged", "oldValue", N4));

        assertEquals("", new PlainFormatter().format(diff));
    }
}

