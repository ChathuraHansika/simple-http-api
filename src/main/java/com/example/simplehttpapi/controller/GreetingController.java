package com.example.simplehttpapi.controller;

import com.example.simplehttpapi.dto.GreetingResponse;
import com.example.simplehttpapi.exception.InvalidInputException;
import com.example.simplehttpapi.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    /**
     * Greets the caller by name.
     *
     * @param name the name, must not be blank and must start with a letter from A to M
     * @return a greeting for the given name
     * @throws InvalidInputException if the name is missing, blank, or starts with a letter from N to Z
     */
    @GetMapping("/hello-world")
    public GreetingResponse helloWorld(@RequestParam(required = false) String name) {
        log.info("Received GET /hello-world request");

        if (!StringUtils.hasText(name)) {
            throw new InvalidInputException();
        }
        return new GreetingResponse(greetingService.greet(name));
    }
}
