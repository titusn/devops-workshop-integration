package nl.xillio.devops.controllers;

import nl.xillio.devops.ExifData;
import nl.xillio.devops.services.ExifService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
public class ScanController {
    private final ExifService exifService;

    public ScanController(ExifService exifService) {
        this.exifService = exifService;
    }

    @GetMapping("/scan")
    public void getScan() {
        throw new MethodNotAllowedException("Use POST to scan an image");
    }

    @PostMapping(value = "/scan", consumes = "*/*")
    public ExifData scanImage(HttpServletRequest request) throws IOException {
        Path tempFile = Files.createTempFile("exif", "");

        try(InputStream data = request.getInputStream()) {
            Files.copy(data, tempFile, StandardCopyOption.REPLACE_EXISTING);

            return exifService.read(tempFile);

        } finally {
            Files.delete(tempFile);
        }
    }
}
