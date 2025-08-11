package hexlet.code;

import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

public class FormatterFactory {
    public static Formatter get(String name) {
        if (name.equals("stylish")) {
            return new StylishFormatter();
        } else if (name.isEmpty()) {
            return new StylishFormatter();
        } else if (name.equals("plain")) {
            return new PlainFormatter();
        } else {
            throw new RuntimeException("Unknown format");
        }
    }
}
