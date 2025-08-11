package hexlet.code;

import java.util.Map;
import java.util.List;

public class StylishFormatter implements Formatter {
    @Override
    public String format(List<Map<String, Object>> diff) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");

        for (var item : diff) {
            Object key = item.get("key");
            Object status = item.get("status");

            switch (status.toString()) {
                case "unchanged" :
                    result.append(formatLine("    ", key, item.get("oldValue")));
                    break;
                case "removed" :
                    result.append(formatLine("  - ", key, item.get("oldValue")));
                    break;
                case "added" :
                    result.append(formatLine("  + ", key, item.get("newValue")));
                    break;
                case "changed" :
                    result.append(formatLine("  - ", key, item.get("oldValue")));
                    result.append(formatLine("  + ", key, item.get("newValue")));
                    break;
                default: throw new RuntimeException("Unknown status");
            }
        }
        result.append("}");
        return result.toString();
    }

    private String formatLine(String sign, Object key, Object value) {
        return sign + key + ": " + isNull(value) + "\n";
    }

    private String isNull(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.toString();
    }
}
