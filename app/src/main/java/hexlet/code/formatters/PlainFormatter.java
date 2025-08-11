package hexlet.code.formatters;

import hexlet.code.Formatter;

import java.util.Map;
import java.util.List;

public class PlainFormatter implements Formatter {
    @Override
    public String format(List<Map<String, Object>> diff) {
        StringBuilder result = new StringBuilder();
        result.append("\n");

        for (var item : diff) {
            Object key = item.get("key");
            Object status = item.get("status");


            switch (status.toString()) {
                case "unchanged" :
                    break;
                case "removed" :
                    result.append(formatLine(key));
                    break;
                case "added" :
                    result.append(formatLine(key, item.get("newValue")));
                    break;
                case "changed" :
                    result.append(formatLine(key, item.get("oldValue"), item.get("newValue")));
                    break;
                default: throw new RuntimeException("Unknown status");
            }
        }
        return result.toString();
    }

    private String formatLine(Object key) {
        return "Property "
                + createQuotationMarks(key)
                + " was removed"
                + "\n";
    }

    private String formatLine(Object key, Object value) {
        return "Property "
                + createQuotationMarks(key)
                + " was added with value: "
                + processingValue(value)
                + "\n";
    }

    private String formatLine(Object key, Object oldValue, Object newValue) {
        return "Property "
                + createQuotationMarks(key)
                + " was updated. From "
                + processingValue(oldValue)
                + " to "
                + processingValue(newValue)
                + "\n";
    }

    private String processingValue(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof String) {
            return createQuotationMarks(obj);
        }
        if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        }
        return "[complex value]";
    }

    private String createQuotationMarks(Object value) {
        return "'" + value + "'";
    }
}
