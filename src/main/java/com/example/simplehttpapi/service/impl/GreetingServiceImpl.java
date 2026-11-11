package com.example.simplehttpapi.service.impl;

import com.example.simplehttpapi.service.GreetingService;
import com.example.simplehttpapi.validation.NameValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {

    private static final Logger log = LoggerFactory.getLogger(GreetingServiceImpl.class);

    private final NameValidator nameValidator;

    public GreetingServiceImpl(NameValidator nameValidator) {
        this.nameValidator = nameValidator;
    }

    @Override
    public String greet(String name) {

        log.info("Validating the name");
        nameValidator.validate(name);

        String trimmedName = name.strip();

        String greeting = "Hello " + Character.toUpperCase(trimmedName.charAt(0))
                + trimmedName.substring(1);

        log.info("Greeting generated successfully");
        return greeting;
    }
}
