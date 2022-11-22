package software.sigma.bu003.internship.coparts.client.technomir.exception;

import org.springframework.web.client.RestClientException;

public class TechnomirClientException extends RuntimeException {
    public TechnomirClientException(RestClientException ex) {
        super(ex.getMessage());
    }
}
