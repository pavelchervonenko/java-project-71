import hexlet.code.Formatter;
import hexlet.code.formatters.PlainFormatter;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlainFormatterTest {
    @Test
    void mixed() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("k", "v");

        List<Integer> list = List.of(1, 2, 3);

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
                "\n"
                + "Property 'cfg' was added with value: [complex value]\n"
                + "Property 'data' was added with value: [complex value]\n"
                + "Property 'host' was updated. From [complex value] to [complex value]\n"
                + "Property 'host2' was updated. From [complex value] to false\n"
                + "Property 'value' was removed\n";

        assertEquals(expected, actual);
    }

    @Test
    void empty() {
        assertEquals("\n", new PlainFormatter().format(List.of()));
    }

    @Test
    void onlyAdded() {
        List<Map<String, Object>> diff = List.of(
                Map.of("key", "a", "status", "added", "newValue", List.of(1, 2)),
                Map.of("key", "b", "status", "added", "newValue", 30));


        String expected =
                "\n"
                + "Property 'a' was added with value: [complex value]\n"
                + "Property 'b' was added with value: 30\n";

        assertEquals(expected, new PlainFormatter().format(diff));
    }

    @Test
    void onlyRemoved() {
        List<Map<String, Object>> diff = List.of(
                Map.of("key", "c", "status", "removed", "oldValue", List.of(3, 4)),
                Map.of("key", "d", "status", "removed", "oldValue", "pavel"));


        String expected =
                "\n"
                + "Property 'c' was removed\n"
                + "Property 'd' was removed\n";

        assertEquals(expected, new PlainFormatter().format(diff));
    }

    @Test
    void onlyChanged() {
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("key", "x");
        map1.put("status", "changed");
        map1.put("oldValue", null);
        map1.put("newValue", 5);

        Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("key", "y");
        map2.put("status", "changed");
        map2.put("oldValue", true);
        map2.put("newValue", null);

        List<Map<String, Object>> diff = List.of(map1, map2);

        String expected =
                "\n"
                + "Property 'x' was updated. From null to 5\n"
                + "Property 'y' was updated. From true to null\n";

        assertEquals(expected, new PlainFormatter().format(diff));
    }

    @Test
    void onlyUnchanged() {
        List<Map<String, Object>> diff = List.of(
                Map.of("key", "a", "status", "unchanged", "oldValue", 10));

        assertEquals("\n", new PlainFormatter().format(diff));
    }
}

