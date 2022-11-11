package software.sigma.bu003.internship.vedmid_andrii.controller;

import com.mongodb.MongoWriteException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import software.sigma.bu003.internship.vedmid_andrii.service.exception.PartNotFoundException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(PartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> partNotFoundException(PartNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(MongoWriteException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> partAlreadyCreated() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("This Entity already created.");
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> partAlreadyCreated(ConstraintViolationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
