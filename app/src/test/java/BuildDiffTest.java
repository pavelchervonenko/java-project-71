package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class BuildDiffTest {
    private static final int N1 = 1;
    private static final int N2 = 2;
    private static final int N3 = 3;
    private static final int N4 = 4;


    @Test
    void mixed() {
        Map<String, Object> first = new HashMap<>();
        first.put("m", N1);
        first.put("a", "x");
        first.put("r", true);

        Map<String, Object> second = new HashMap<>();
        second.put("m", N1);
        second.put("a", "y");
        second.put("z", N2);

        List<Map<String, Object>> diff = Differ.buildDiff(first, second);

        List<Map<String, Object>> expected = new ArrayList<>();

        Map<String, Object> ea = new HashMap<>();
        ea.put("key", "a");
        ea.put("status", "changed");
        ea.put("oldValue", "x");
        ea.put("newValue", "y");
        expected.add(ea);

        Map<String, Object> em = new HashMap<>();
        em.put("key", "m");
        em.put("status", "unchanged");
        em.put("oldValue", N1);
        expected.add(em);

        Map<String, Object> er = new HashMap<>();
        er.put("key", "r");
        er.put("status", "removed");
        er.put("oldValue", true);
        expected.add(er);

        Map<String, Object> ez = new HashMap<>();
        ez.put("key", "z");
        ez.put("status", "added");
        ez.put("newValue", N2);
        expected.add(ez);

        assertEquals(expected, diff);
    }

    @Test
    void empty() {
        Map<String, Object> first = Map.of();
        Map<String, Object> second = Map.of();

        List<Map<String, Object>> diff = Differ.buildDiff(first, second);

        assertEquals(List.of(), diff);
    }

    @Test
    void onlyAdded() {
        Map<String, Object> first = Map.of();
        Map<String, Object> second = new HashMap<>();
        second.put("b", N2);
        second.put("a", N1);

        List<Map<String, Object>> diff = Differ.buildDiff(first, second);

        List<Map<String, Object>> expected = List.of(
                Map.of("key", "a", "status", "added", "newValue", N1),
                Map.of("key", "b", "status", "added", "newValue", N2)
        );

        assertEquals(expected, diff);
    }

    @Test
    void onlyRemoved() {
        Map<String, Object> first = new HashMap<>();
        first.put("z", N3);
        first.put("x", N4);
        Map<String, Object> second = Map.of();

        List<Map<String, Object>> diff = Differ.buildDiff(first, second);

        List<Map<String, Object>> expected = List.of(
                Map.of("key", "x", "status", "removed", "oldValue", N4),
                Map.of("key", "z", "status", "removed", "oldValue", N3)
        );

        assertEquals(expected, diff);
    }

    @Test
    void onlyChanged() {
        Map<String, Object> first = new HashMap<>();
        first.put("a", "old");
        first.put("b", N1);
        first.put("c", null);

        Map<String, Object> second = new HashMap<>();
        second.put("a", "new");
        second.put("b", N2);
        second.put("c", N3);

        List<Map<String, Object>> diff = Differ.buildDiff(first, second);

        Map<String, Object> e1 = new HashMap<>();
        e1.put("key", "a");
        e1.put("status", "changed");
        e1.put("oldValue", "old");
        e1.put("newValue", "new");

        Map<String, Object> e2 = new HashMap<>();
        e2.put("key", "b");
        e2.put("status", "changed");
        e2.put("oldValue", N1);
        e2.put("newValue", N2);

        Map<String, Object> e3 = new HashMap<>();
        e3.put("key", "c");
        e3.put("status", "changed");
        e3.put("oldValue", null);
        e3.put("newValue", N3);

        List<Map<String, Object>> expected = List.of(e1, e2, e3);
        assertEquals(expected, diff);
    }
}
