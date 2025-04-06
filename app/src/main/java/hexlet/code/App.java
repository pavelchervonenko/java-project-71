package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
class App implements Runnable {
    @Override
    public void run() {
        System.out.println("Start...");
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}