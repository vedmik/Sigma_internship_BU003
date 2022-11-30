package software.sigma.bu003.internship.coparts.client.technomir.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalTechnomirClientExceptionHandler {

    @ExceptionHandler(TechnomirEmptyStockException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String technomirEmptyStockException(TechnomirEmptyStockException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ExceptionHandler(TechnomirPartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String technomirPartNotFoundException(TechnomirPartNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ExceptionHandler(TechnomirClientException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public String technomirClientException(TechnomirClientException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }
}
