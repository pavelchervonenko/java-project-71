package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;


class DifferTest {
    private static String readResource(String name) throws IOException {
        try (InputStream input = DifferTest.class.getClassLoader().getResourceAsStream(name)) {
            if (input == null) {
                throw new IllegalArgumentException("Resource not found: " + name);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    private static String resourcePath(String resourceName) {
        URL url = DifferTest.class.getClassLoader().getResource(resourceName);
        if (url == null) {
            throw new IllegalArgumentException("Resource not found: " + resourceName);
        }
        try {
            return Paths.get(url.toURI()).toAbsolutePath().normalize().toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Bad resource URI: " + resourceName, e);
        }
    }

    private static final String FILE2_JSON = resourcePath("nested_file2.json");
    private static final String FILE2_YAML = resourcePath("nested_file2.yaml");
    private static final String FILE1_JSON = resourcePath("nested_file1.json");
    private static final String FILE1_YAML = resourcePath("nested_file1.yaml");

    @Test
    void generateYmlJsonTest() throws IOException {
        String expected = readResource("DifferTest_for_json_format.json");
        String actual = Differ.generate(FILE1_YAML, FILE2_YAML, "json");

        ObjectMapper mapper = new ObjectMapper();

        JsonNode exp = mapper.readTree(expected);
        JsonNode act = mapper.readTree(actual);

        assertEquals(exp, act);
    }

    @Test
    void generateYmlStylishTest() throws IOException {
        String expected = "{\n"
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

        String actual = Differ.generate(FILE1_YAML, FILE2_YAML, "stylish");

        assertEquals(expected, actual);
    }

    @Test
    void generateYmlPlainTest() throws IOException {
        String expected =
                "Property 'chars2' was updated. From [complex value] to false\n"
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
                + "Property 'setting3' was updated. From true to 'none'";

        String actual = Differ.generate(FILE1_YAML, FILE2_YAML, "plain");

        assertEquals(expected, actual);
    }

    @Test
    void generateYmlDefaultTest() throws IOException {
        String expected = "{\n"
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

        String actual = Differ.generate(FILE1_YAML, FILE2_YAML);


        assertEquals(expected, actual);
    }

    @Test
    void generateJsonJsonTest() throws IOException {
        String expected = readResource("DifferTest_for_json_format.json");
        String actual = Differ.generate(FILE1_JSON, FILE2_JSON, "json");

        ObjectMapper mapper = new ObjectMapper();

        JsonNode exp = mapper.readTree(expected);
        JsonNode act = mapper.readTree(actual);

        assertEquals(exp, act);
    }

    @Test
    void generateJsonStylishTest() throws IOException {
        String expected = "{\n"
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

        String actual = Differ.generate(FILE1_JSON, FILE2_JSON, "stylish");

        assertEquals(expected, actual);
    }

    @Test
    void generateJsonPlainTest() throws IOException {
        String expected =
                "Property 'chars2' was updated. From [complex value] to false\n"
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
                        + "Property 'setting3' was updated. From true to 'none'";

        String actual = Differ.generate(FILE1_JSON, FILE2_JSON, "plain");

        assertEquals(expected, actual);
    }

    @Test
    void generateJsonDefaultTest() throws IOException {
        String expected = "{\n"
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

        String actual = Differ.generate(FILE1_JSON, FILE2_JSON);

        assertEquals(expected, actual);
    }
}
