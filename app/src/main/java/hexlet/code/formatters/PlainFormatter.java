package hexlet.code.formatters;

import hexlet.code.Formatter;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;


public class PlainFormatter implements Formatter {
    @Override
    public final String format(List<Map<String, Object>> diff) {

        List<String> lines = new ArrayList<>();

        for (var item : diff) {
            Object key = item.get("key");
            Object status = item.get("status");


            switch (status.toString()) {
                case "unchanged" :
                    break;
                case "removed" :
                    var removed = "Property '"
                            + key
                            + "' was removed";
                    lines.add(removed);
                    break;
                case "added" :
                    var added = "Property '"
                            + key
                            + "' was added with value: "
                            + processingValue(item.get("newValue"));
                    lines.add(added);
                    break;
                case "changed" :
                    var changed = "Property '"
                            + key
                            + "' was updated. From "
                            + processingValue(item.get("oldValue"))
                            + " to "
                            + processingValue(item.get("newValue"));
                    lines.add(changed);
                    break;
                default: throw new RuntimeException("Unknown status in diff. Transmitted status: " + status);
            }
        }
        return String.join("\n", lines);
    }

    private String processingValue(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof String) {
            return "'" + obj + "'";
        }
        if (obj instanceof Map<?, ?>
                || obj instanceof Iterable<?>
                || obj.getClass().isArray()) {
            return "[complex value]";
        }
        return String.valueOf(obj);
    }
}
