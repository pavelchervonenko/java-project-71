package hexlet.code;

import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

public class FormatterFactory {
    public static Formatter get(String name) {
        return switch (name.toLowerCase()) {
            case "stylish", "" -> new StylishFormatter();
            case "plain" -> new PlainFormatter();
            case "json" -> new JsonFormatter();
            default -> throw new RuntimeException("Unknown format was transmitted: " + name);
        };
    }
}
