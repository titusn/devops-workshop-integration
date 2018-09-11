package nl.xillio.devops.services;

import nl.xillio.devops.ExifData;
import nl.xillio.devops.ExifProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

@Service
public class ExifService {
    private final ExifProperties properties;

    public ExifService(ExifProperties properties) {
        this.properties = properties;
    }

    public ExifData read(Path file) throws IOException {

        Process process = new ProcessBuilder()
                .command(properties.getExecutable(), file.toAbsolutePath().toString())
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start();

        ExifData result = new ExifData();

        try (Scanner scanner = new Scanner(process.getInputStream())) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int colon = line.indexOf(':');
                if (colon > 0) {
                    String name = line.substring(0, colon).trim();
                    String value = line.substring(colon + 1).trim();

                    // Make camelCase name
                    name = name.replaceAll("\\s", "");
                    name = name.substring(0, 1).toLowerCase() + name.substring(1);

                    result.put(
                            name.trim(),
                            value.trim()
                    );
                }
            }
        }

        return result;
    }
}
