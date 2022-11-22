package software.sigma.bu003.internship.coparts.client.technomir.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalTechnomirClientExceptionHandler {

    @ExceptionHandler(ObjectIsEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String objectIsEmptyException(ObjectIsEmptyException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(PartIsEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String partIsEmptyException(PartIsEmptyException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(TechnomirClientException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public String technomirClientException(TechnomirClientException ex) {
        return ex.getMessage();
    }
}
