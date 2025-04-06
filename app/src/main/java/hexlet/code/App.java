package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.File;

@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
class App implements Runnable {

    @Parameters(index = "0",
            description = "path to first file")
    private File filepath1;

    @Parameters(index = "1",
            description = "path to second file")
    private File filepath2;

    @Option(names = {"-f", "--format"},
            description = "output format [default: stylish]")
    private String format;


    @Override
    public void run() {
        System.out.println("Start...");
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}