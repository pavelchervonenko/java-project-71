package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class Parser {
    public static Map<String, Object> parse(Path path) throws IOException {
        String filename = path.getFileName().toString();
        ObjectMapper mapper;

        if (filename.endsWith(".json")) {
            mapper = new ObjectMapper();
        } else if (filename.endsWith(".yml") || filename.endsWith(".yaml")) {
            mapper = new YAMLMapper();
        } else {
            throw new IOException("The format is not supported");
        }

        return mapper.readValue(path.toFile(), new TypeReference<>() { });
    }
}
