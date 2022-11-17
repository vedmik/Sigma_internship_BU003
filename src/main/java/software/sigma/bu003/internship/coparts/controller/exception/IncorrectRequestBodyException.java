package software.sigma.bu003.internship.coparts.controller.exception;

import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class IncorrectRequestBodyException extends RuntimeException {
    public IncorrectRequestBodyException(BindingResult bindingResult) {
        super(bindingResult.getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("\n")));
    }
}
