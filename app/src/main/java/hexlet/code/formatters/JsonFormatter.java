package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.Formatter;

import java.util.List;
import java.util.Map;


public class JsonFormatter implements Formatter {

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

    @Override
    public final String format(List<Map<String, Object>> diff) {
        try {
            return mapper.writeValueAsString(diff);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize diff to JSON", e);
        }
    }
}
