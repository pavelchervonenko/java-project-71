package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import java.util.List;
import java.util.Map;


class DifferTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testJsonComparison() throws Exception {
        var path1 = Path.of("src/test/resources/file1.json");
        var path2 = Path.of("src/test/resources/file2.json");

        Map<String, Object> data1 = mapper.readValue(path1.toFile(),
                new TypeReference<>() { });
        Map<String, Object> data2 = mapper.readValue(path2.toFile(),
                new TypeReference<>() { });

        List<String> diff = Differ.generate(data1, data2);

        String expected =
                "  - follow: false\n"
                + "    host: hexlet.io\n"
                + "  - proxy: 123.234.53.22\n"
                + "  - timeout: 50\n"
                + "  + timeout: 20\n"
                + "  + verbose: true";

        assertEquals(List.of(expected.split("\n")), diff);
    }

    @Test
    void testFirstEmpty() throws Exception {
        Map<String, Object> data1 = Map.of();
        Map<String, Object> data2 = Map.of("key", "value");

        List<String> diff = Differ.generate(data1, data2);

        String expected =
                "  + key: value";

        assertEquals(List.of(expected), diff);
    }

    @Test
    void testBothEmpty() throws Exception {
        Map<String, Object> data1 = Map.of();
        Map<String, Object> data2 = Map.of();

        List<String> diff = Differ.generate(data1, data2);

        assertEquals(List.of(), diff);
    }
}
