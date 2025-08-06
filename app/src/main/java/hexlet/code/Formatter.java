package hexlet.code;

import java.util.List;
import java.util.Map;

public interface Formatter {
    String format(List<Map<String, Object>> diff);
}
