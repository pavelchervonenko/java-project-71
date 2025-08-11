package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


class DifferTest {
    ObjectMapper mapper;

    @Test
    void testStylishFormatter() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "2");
        map.put(3, "4");

        List<Map<String, Object>> diff = List.of(
                Map.of("key", "host", "status", "unchanged", "oldValue", "hexlet.io"),
                Map.of("key", "proxy", "status", "changed", "oldValue", "123.123.12.13", "newValue", "122.121.10.11"),
                Map.of("key", "timeout", "status", "removed", "oldValue", 50),
                Map.of("key", "data", "status", "added", "newValue", List.of(1, 2, 3, 4, 5)),
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
    void testEmptyFormatter() {
        Formatter formatter = new StylishFormatter();
        String actual = formatter.format(List.of());

        String expected = "{\n}";

        assertEquals(expected, actual);
    }

    @Test
    void testFormatterFactory() {
        Formatter formatter = FormatterFactory.get("stylish");
        assertEquals(StylishFormatter.class, formatter.getClass());
    }


    @Test
    void testComparisonJson() throws Exception {
        var path1 = Path.of("src/test/resources/nested_file1.json");
        var path2 = Path.of("src/test/resources/nested_file2.json");

        mapper = new ObjectMapper();

        Map<String, Object> data1 = mapper.readValue(path1.toFile(),
                new TypeReference<>() { });
        Map<String, Object> data2 = mapper.readValue(path2.toFile(),
                new TypeReference<>() { });

        List<Map<String, Object>> diff = Differ.generate(data1, data2);

        Formatter formatter = new StylishFormatter();
        String actual = formatter.format(diff);

        String expected =
                "{\n"
                + "    chars1: [a, b, c]\n"
                + "  - chars2: [d, e, f]\n"
                + "  + chars2: false\n"
                + "  - checked: false\n"
                + "  + checked: true\n"
                + "  - default: null\n"
                + "  + default: [value1, value2]\n"
                + "  - id: 45\n"
                + "  + id: null\n"
                + "  - key1: value1\n"
                + "  + key2: value2\n"
                + "    numbers1: [1, 2, 3, 4]\n"
                + "  - numbers2: [2, 3, 4, 5]\n"
                + "  + numbers2: [22, 33, 44, 55]\n"
                + "  - numbers3: [3, 4, 5]\n"
                + "  + numbers4: [4, 5, 6]\n"
                + "  + obj1: {nestedKey=value, isNested=true}\n"
                + "  - setting1: Some value\n"
                + "  + setting1: Another value\n"
                + "  - setting2: 200\n"
                + "  + setting2: 300\n"
                + "  - setting3: true\n"
                + "  + setting3: none\n"
                + "}";

        assertEquals(expected, actual);
    }

    @Test
    void testComparisonJsonWithNull() {
        Map<String, Object> entry = new HashMap<>();
        entry.put("key", "setting");
        entry.put("status", "changed");
        entry.put("oldValue", null);
        entry.put("newValue", 123);

        List<Map<String, Object>> diff = List.of(entry);

        Formatter formatter = new StylishFormatter();
        String actual = formatter.format(diff);

        String expected =
                "{\n"
                + "  - setting: null\n"
                + "  + setting: 123\n"
                + "}";

        assertEquals(expected, actual);
    }

    @Test
    void testCompareJsonPlain() throws IOException {
        var path1 = Path.of("src/test/resources/nested_file1.json");
        var path2 = Path.of("src/test/resources/nested_file2.json");

        mapper = new ObjectMapper();

        Map<String, Object> data1 = mapper.readValue(path1.toFile(),
                new TypeReference<>() { });
        Map<String, Object> data2 = mapper.readValue(path2.toFile(),
                new TypeReference<>() { });

        List<Map<String, Object>> diff = Differ.generate(data1, data2);

        Formatter formatter = new PlainFormatter();
        String actual = formatter.format(diff);

        String expected =
                "\n"
                + "Property 'chars2' was updated. From [complex value] to false\n"
                + "Property 'checked' was updated. From false to true\n"
                + "Property 'default' was updated. From null to [complex value]\n"
                + "Property 'id' was updated. From 45 to null\n"
                + "Property 'key1' was removed\n"
                + "Property 'key2' was added with value: 'value2'\n"
                + "Property 'numbers2' was updated. From [complex value] to [complex value]\n"
                + "Property 'numbers3' was removed\n"
                + "Property 'numbers4' was added with value: [complex value]\n"
                + "Property 'obj1' was added with value: [complex value]\n"
                + "Property 'setting1' was updated. From 'Some value' to 'Another value'\n"
                + "Property 'setting2' was updated. From 200 to 300\n"
                + "Property 'setting3' was updated. From true to 'none'"
                + "\n";

        assertEquals(expected, actual);
    }
}
