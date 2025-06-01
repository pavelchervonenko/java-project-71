package hexlet.code;

import java.util.Map;
import java.util.LinkedList;
import java.util.TreeSet;


public class Differ {
    public static void generate(Map<String, Object> dataFirst, Map<String, Object> dataSecond) {
        var allKeys = new TreeSet<>();

        allKeys.addAll(dataFirst.keySet());
        allKeys.addAll(dataSecond.keySet());

        var result = new LinkedList<String>();

        for (var key: allKeys) {
            boolean keyInFirst = dataFirst.containsKey(key);
            boolean keyInSecond = dataSecond.containsKey(key);

            var valueInFirst = dataFirst.get(key);
            var valueInSecond = dataSecond.get(key);

            if (keyInFirst && keyInSecond) {
                if (valueInFirst.equals(valueInSecond)) {
                    result.add("    " + key + ": " + valueInFirst);
                }
                else {
                    result.add("  - " + key + ": " + valueInFirst);
                    result.add("  + " + key + ": " + valueInSecond);
                }
            }
            else if (keyInFirst) {
                result.add("  - " + key + ": " + valueInFirst);
            }
            else if (keyInSecond) {
                result.add("  + " + key + ": " + valueInSecond);
            }
        }
        System.out.println(String.join("\n", result));
    }
}
