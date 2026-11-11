package com.example.simplehttpapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.example.simplehttpapi.exception.InvalidInputException;
import com.example.simplehttpapi.validation.NameValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GreetingServiceImplTest {

    @Mock
    private NameValidator nameValidator;

    @InjectMocks
    private GreetingServiceImpl greetingService;

    @Test
    void capitalisesFirstLetter() {
        assertThat(greetingService.greet("alice")).isEqualTo("Hello Alice");
    }

    @Test
    void keepsCasingOfTheRestOfTheName() {
        assertThat(greetingService.greet("mcDONALD")).isEqualTo("Hello McDONALD");
    }

    @Test
    void trimsSurroundingWhitespace() {
        assertThat(greetingService.greet("  bob \t")).isEqualTo("Hello Bob");
    }

    @Test
    void handlesSingleLetterName() {
        assertThat(greetingService.greet("a")).isEqualTo("Hello A");
    }

    @Test
    void validatesTheOriginalName() {
        greetingService.greet(" alice ");
        verify(nameValidator).validate(" alice ");
    }

    @Test
    void throwsWhenValidationFails() {
        doThrow(new InvalidInputException()).when(nameValidator).validate("zoe");
        assertThatThrownBy(() -> greetingService.greet("zoe"))
                .isInstanceOf(InvalidInputException.class);
    }
}
