package com.example.simplehttpapi.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.simplehttpapi.exception.InvalidInputException;
import com.example.simplehttpapi.service.GreetingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Covers the endpoint together with GlobalExceptionHandler.
 * Handler logging is turned off so the 500 test doesn't dump a stack trace into the build output.
 */
@WebMvcTest(
        controllers = GreetingController.class,
        properties = "logging.level.com.example.simplehttpapi.exception.GlobalExceptionHandler=OFF")
class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GreetingService greetingService;

    @Test
    void returnsGreetingForValidName() throws Exception {
        when(greetingService.greet("alice")).thenReturn("Hello Alice");

        mockMvc.perform(get("/hello-world").param("name", "alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Alice"));
    }

    @Test
    void returnsBadRequestWhenNameIsMissing() throws Exception {
        mockMvc.perform(get("/hello-world"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));

        verifyNoInteractions(greetingService);
    }

    @Test
    void returnsBadRequestWhenNameIsBlank() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "   "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));

        verifyNoInteractions(greetingService);
    }

    @Test
    void returnsBadRequestWhenServiceRejectsName() throws Exception {
        when(greetingService.greet("zoe")).thenThrow(new InvalidInputException());

        mockMvc.perform(get("/hello-world").param("name", "zoe"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void returnsInternalServerErrorOnUnexpectedFailure() throws Exception {
        when(greetingService.greet(anyString())).thenThrow(new IllegalStateException("boom"));

        mockMvc.perform(get("/hello-world").param("name", "alice"))
                .andExpect(status().isInternalServerError());
    }
}
