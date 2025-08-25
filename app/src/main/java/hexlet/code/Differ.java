package hexlet.code;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.List;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Differ {
    public static String generate(String filePath1, String filePath2) throws IOException {
        return generate(filePath1, filePath2, "stylish");
    }

    public static String generate(String filePath1, String filePath2, String format) throws IOException {
        Path absolutePath1 = Paths.get(filePath1).toAbsolutePath().normalize();
        Path absolutePath2 = Paths.get(filePath2).toAbsolutePath().normalize();

        if (!Files.exists(absolutePath1) || !Files.exists(absolutePath2)) {
            throw new IllegalArgumentException("File does not exist");
        }

        Map<String, Object> dataFirst = readAndParse(absolutePath1);
        Map<String, Object> dataSecond = readAndParse(absolutePath2);

        List<Map<String, Object>> diff = buildDiff(dataFirst, dataSecond);
        Formatter formatter = FormatterFactory.get(format);
        return formatter.format(diff);
    }

    private static Map<String, Object> readAndParse(Path path) throws IOException {
        String text = Files.readString(path, StandardCharsets.UTF_8);
        String format = detectFormat(path);
        return Parser.parse(text, format);
    }

    private static String detectFormat(Path path) throws IOException {
        String name = path.getFileName().toString().toLowerCase();

        if (name.endsWith(".json")) {
            return "json";
        } else if (name.endsWith(".yml") || name.endsWith(".yaml")) {
            return "yaml";
        } else {
            throw new IOException("Unsupported file format: " + name);
        }
    }

    public static List<Map<String, Object>> buildDiff(Map<String, Object> dataFirst, Map<String, Object> dataSecond) {
        var allKeys = new TreeSet<String>();

        allKeys.addAll(dataFirst.keySet());
        allKeys.addAll(dataSecond.keySet());

        var result = new ArrayList<Map<String, Object>>();

        for (String key: allKeys) {
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
