package nl.xillio.devops.controllers;

import nl.xillio.devops.ExifServer;
import okhttp3.*;
import org.springframework.boot.SpringApplication;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class ScanControllerTest {
    private final OkHttpClient http = new OkHttpClient();

    @BeforeClass
    public void startServer() {
        SpringApplication.run(ExifServer.class);
    }

    @Test
    public void whenImageIsScannedPropertiesAreReturned() throws IOException {
        Response response = http.newCall(
                new Request.Builder()
                        .url("http://localhost:8080/scan")
                        .post(RequestBody.create(
                                MediaType.get("image/jpeg"),
                                new File("src/test/resources/picture.jpg")
                        ))
                        .build()
        ).execute();

        assertEquals(response.code(), 200);
    }

}
