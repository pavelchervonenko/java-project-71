package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hexlet.code.Formatter;

import java.util.List;
import java.util.Map;

public class JsonFormatter implements Formatter {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public final String format(List<Map<String, Object>> diff) {
        ArrayNode root = mapper.createArrayNode();

        for (Map<String, Object> item : diff) {
            ObjectNode node = mapper.createObjectNode();

            node.put("key", String.valueOf(item.get("key")));
            node.put("status", String.valueOf(item.get("status")));

            if (item.containsKey("oldValue")) {
                node.set("oldValue", mapper.valueToTree(item.get("oldValue")));
            }
            if (item.containsKey("newValue")) {
                node.set("newValue", mapper.valueToTree(item.get("newValue")));
            }

            root.add(node);
        }

        return root.toPrettyString();
    }
}
