package software.sigma.bu003.internship.coparts.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import software.sigma.bu003.internship.coparts.part.service.exception.PartAlreadyCreatedException;
import software.sigma.bu003.internship.coparts.part.service.exception.PartNotFoundException;
import software.sigma.bu003.internship.coparts.user.service.exception.UserAlreadyExistsException;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(PartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String partNotFoundException(PartNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ExceptionHandler(PartAlreadyCreatedException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String partHasAlreadyBeenCreated(PartAlreadyCreatedException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ExceptionHandler(IncorrectRequestBodyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String partAlreadyCreated(IncorrectRequestBodyException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String userIsNotIdentify(UsernameNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String userAlreadyExists(UserAlreadyExistsException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }
}
