package com.example.simplehttpapi.validation;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.simplehttpapi.exception.InvalidInputException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NameValidatorTest {

    private final NameValidator nameValidator = new NameValidator();

    @ParameterizedTest
    @ValueSource(strings = {"alice", "Alice", "mike", "Mike", "Bob", "j"})
    void validate_acceptsNamesStartingWithAToM(String name) {
        assertThatCode(() -> nameValidator.validate(name)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"nick", "Nick", "zoe", "Zoe", "Oscar"})
    void validate_rejectsNamesStartingWithNToZ(String name) {
        assertThatThrownBy(() -> nameValidator.validate(name))
                .isInstanceOf(InvalidInputException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1alice", "_bob", "-carl", "éric", "Ägnes"})
    void validate_rejectsNamesNotStartingWithEnglishLetter(String name) {
        assertThatThrownBy(() -> nameValidator.validate(name))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void validate_ignoresSurroundingWhitespace() {
        assertThatCode(() -> nameValidator.validate("  alice  ")).doesNotThrowAnyException();
        assertThatThrownBy(() -> nameValidator.validate("  zoe  "))
                .isInstanceOf(InvalidInputException.class);
    }
}
