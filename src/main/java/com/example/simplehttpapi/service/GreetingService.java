package com.example.simplehttpapi.service;

/** Builds greetings for valid names. */
public interface GreetingService {

    /**
     * Creates a greeting for the given name.
     *
     * Trims spaces at the beginning and end, and capitalises only the first letter.
     * The rest of the name keeps its original casing
     *
     * @param name the name to greet
     * @return the greeting
     */
    String greet(String name);
}
