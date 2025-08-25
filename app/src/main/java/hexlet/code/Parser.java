package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.util.Map;

public class Parser {
    private static final ObjectMapper JSON = new ObjectMapper();
    private static final ObjectMapper YAML = new YAMLMapper();


    public static Map<String, Object> parse(String text, String format) throws IOException {
        ObjectMapper mapper;

        if ("json".equals(format)) {
            mapper = JSON;
        } else if ("yaml".equals(format) || "yml".equals(format)) {
            mapper = YAML;
        } else {
            throw new IllegalArgumentException("Unknown file format: " + format);
        }

        return mapper.readValue(text, new TypeReference<Map<String, Object>>() { });

    }
}
