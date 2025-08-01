package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.Map;
import java.util.concurrent.Callable;

@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
class App implements Callable<Integer> {

    @Parameters(index = "0",
            description = "path to first file")
    private String filepathFirst;

    @Parameters(index = "1",
            description = "path to second file")
    private String filepathSecond;

    @Option(names = {"-f", "--format"},
            description = "output format [default: stylish]")
    private String format;


    @Override
    public Integer call() throws Exception {
        Path absolutePathFirst = Paths.get(filepathFirst).toAbsolutePath().normalize();
        Path absolutePathSecond = Paths.get(filepathSecond).toAbsolutePath().normalize();

        if (!Files.exists(absolutePathFirst)) {
            throw new Exception("File '" + absolutePathFirst + "' does not exist");
        }
        if (!Files.exists(absolutePathSecond)) {
            throw new Exception("File '" + absolutePathSecond + "' does not exist");
        }

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> dataFirst = objectMapper.readValue(
                absolutePathFirst.toFile(),
                new TypeReference<Map<String, Object>>() { });
        Map<String, Object> dataSecond = objectMapper.readValue(
                absolutePathSecond.toFile(),
                new TypeReference<Map<String, Object>>() { });

        var lines = Differ.generate(dataFirst, dataSecond);
        System.out.println(String.join("\n", lines));
        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
