package hexlet.code;

public class FormatterFactory {
    public static Formatter get(String name) {
        if (name.equals("stylish")) {
            return new StylishFormatter();
        } else if (name.isEmpty()) {
            return new StylishFormatter();
        } else {
            throw new RuntimeException("Unknown format");
        }
    }
}
