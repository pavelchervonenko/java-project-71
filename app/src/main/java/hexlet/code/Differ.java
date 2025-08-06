package hexlet.code;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Objects;
import java.util.HashMap;


public class Differ {
    public static List<Map<String, Object>> generate(Map<String, Object> dataFirst, Map<String, Object> dataSecond) {
        var allKeys = new TreeSet<>();

        allKeys.addAll(dataFirst.keySet());
        allKeys.addAll(dataSecond.keySet());

        var result = new ArrayList<Map<String, Object>>();

        for (var key: allKeys) {
            boolean keyInFirst = dataFirst.containsKey(key);
            boolean keyInSecond = dataSecond.containsKey(key);

            Object valueInFirst = dataFirst.get(key);
            Object valueInSecond = dataSecond.get(key);

            Map<String, Object> entry = new HashMap<>();
            entry.put("key", key);

            if (keyInFirst && !keyInSecond) {
                entry.put("status", "removed");
                entry.put("oldValue", valueInFirst);
            } else if (!keyInFirst && keyInSecond) {
                entry.put("status", "added");
                entry.put("newValue", valueInSecond);
            } else if (Objects.equals(valueInSecond, valueInFirst)) {
                entry.put("status", "unchanged");
                entry.put("oldValue", valueInFirst);
            } else {
                entry.put("status", "changed");
                entry.put("oldValue", valueInFirst);
                entry.put("newValue", valueInSecond);
            }
            result.add(entry);
        }
        return result;
    }
}
