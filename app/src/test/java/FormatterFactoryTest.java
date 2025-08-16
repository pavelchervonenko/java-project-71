import hexlet.code.Formatter;
import hexlet.code.FormatterFactory;
import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatterFactoryTest {
    @Test
    void testFormatterFactorStylish() {
        Formatter formatter = FormatterFactory.get("stylish");
        assertEquals(StylishFormatter.class, formatter.getClass());
    }

    @Test
    void testFormatterFactorEmpty() {
        Formatter formatter = FormatterFactory.get("");
        assertEquals(StylishFormatter.class, formatter.getClass());
    }

    @Test
    void testFormatterFactoryPlain() {
        Formatter formatter = FormatterFactory.get("plain");
        assertEquals(PlainFormatter.class, formatter.getClass());
    }

    @Test
    void testFormatterFactoryJson() {
        Formatter formatter = FormatterFactory.get("json");
        assertEquals(JsonFormatter.class, formatter.getClass());
    }
}
