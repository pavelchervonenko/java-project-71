import hexlet.code.Parser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ParserTest {
    @TempDir
    Path temp;

    private Path writeFile(String name, String content) throws IOException {
        Path path = temp.resolve(name);
        Files.writeString(path, content);
        return path;
    }

    @Test
    void parseJson() throws Exception {
        String json = """
                {
                  "a": 1,
                  "b": "x",
                  "c": null,
                  "arr": [1, 2]
                }
                """;

        Path file = writeFile("sample.json", json);

        Map<String, Object> map = Parser.parse(file);

        assertEquals(1, map.get("a"));
        assertEquals("x", map.get("b"));
        assertEquals(List.of(1, 2), map.get("arr"));
        assertNull(map.get("c"));
    }

    @Test
    void parseYml() throws Exception {
        String yml = """
                a: 1
                b: x
                c: null
                arr: [1, 2]
                obj:
                  k: v
                """;
        Path file = writeFile("sample.yml", yml);

        Map<String, Object> m = Parser.parse(file);

        assertEquals(1, m.get("a"));
        assertEquals("x", m.get("b"));
        assertEquals(List.of(1, 2), m.get("arr"));
        assertNull(m.get("c"));
    }

    @Test
    void parseYaml() throws Exception {
        String yaml = """
            a: 1
            b: x
            arr:
              - 10
              - 20
            """;
        Path file = writeFile("sample.yaml", yaml);

        Map<String, Object> m = Parser.parse(file);

        assertEquals(1, m.get("a"));
        assertEquals("x", m.get("b"));
        assertEquals(List.of(10, 20), m.get("arr"));
    }

}
