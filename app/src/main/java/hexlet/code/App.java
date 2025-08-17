package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
class App implements Callable<Integer> {

    @Parameters(index = "0",
            description = "path to first file")
    private String filePath1;

    @Parameters(index = "1",
            description = "path to second file")
    private String filePath2;

    @Option(names = {"-f", "--format"},
            description = "output format [default: stylish]",
            defaultValue = "stylish")
    private String format;

    private static final int SUCCESS = 0;
    private static final int FILE_MISSING_FAILED = 2;
    private static final int READ_PARSE_FAILED = 3;

    @Override
    public Integer call() {
        try {
            String output = Differ.generate(filePath1, filePath2, format);
            System.out.println(output);
            return SUCCESS;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return FILE_MISSING_FAILED;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return READ_PARSE_FAILED;
        }
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
