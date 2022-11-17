package software.sigma.bu003.internship.coparts.controller.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import software.sigma.bu003.internship.coparts.service.exception.PartAlreadyCreatedException;
import software.sigma.bu003.internship.coparts.service.exception.PartNotFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(PartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String partNotFoundException(PartNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(PartAlreadyCreatedException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String partHasAlreadyBeenCreated(PartAlreadyCreatedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IncorrectRequestBodyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String partAlreadyCreated(IncorrectRequestBodyException ex) {
        return ex.getMessage();
    }
}
