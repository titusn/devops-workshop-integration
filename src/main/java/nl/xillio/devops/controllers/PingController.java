package nl.xillio.devops.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class PingController {

    @GetMapping("/ping")
    public Map<String, Object> ping() {
        return Collections.singletonMap("OK", true);
    }
}
