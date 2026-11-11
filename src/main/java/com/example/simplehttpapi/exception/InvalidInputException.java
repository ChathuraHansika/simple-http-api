package com.example.simplehttpapi.exception;

/** Thrown when a name does not meet the rules for being greeted. */
public class InvalidInputException extends RuntimeException {

    public InvalidInputException() {
        super("Name must be a single value starting with an English letter from A to M");
    }
}
