package com.example.simplehttpapi.validation;

import com.example.simplehttpapi.exception.InvalidInputException;
import org.springframework.stereotype.Component;

/** Decides whether a name may be greeted. */
@Component
public class NameValidator {

    /**
     * Checks if the name is valid, ignoring spaces at the beginning and end.
     *
     * @param name the name to check
     * @throws InvalidInputException if the name is null, empty, or does not start
     *                               with an English letter from A to M
     */
    public void validate(String name) {
        if (!isInFirstHalfOfAlphabet(name.strip().charAt(0))) {
            throw new InvalidInputException();
        }
    }

    private static boolean isInFirstHalfOfAlphabet(char letter) {
        return (letter >= 'a' && letter <= 'm') || (letter >= 'A' && letter <= 'M');
    }
}
