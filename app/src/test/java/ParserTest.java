import hexlet.code.Parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.List;
import java.util.Map;


public class ParserTest {
    private static final int N1 = 1;
    private static final int N2 = 2;

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


        Map<String, Object> map = Parser.parse(json, "json");

        assertEquals(N1, map.get("a"));
        assertEquals("x", map.get("b"));
        assertEquals(List.of(N1, N2), map.get("arr"));
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

        Map<String, Object> m = Parser.parse(yml, "yaml");

        assertEquals(N1, m.get("a"));
        assertEquals("x", m.get("b"));
        assertEquals(List.of(N1, N2), m.get("arr"));
        assertNull(m.get("c"));
        assertTrue(m.containsKey("obj"));
        assertInstanceOf(Map.class, m.get("obj"));
    }
}
