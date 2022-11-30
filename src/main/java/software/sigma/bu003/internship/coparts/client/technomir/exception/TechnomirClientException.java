package software.sigma.bu003.internship.coparts.client.technomir.exception;

import org.springframework.web.client.RestClientException;
import software.sigma.bu003.internship.coparts.client.technomir.dto.request.TechnomirPayLoad;

public class TechnomirClientException extends RuntimeException {
    public TechnomirClientException(String urlRequest, TechnomirPayLoad tehnomirPayLoad, RestClientException ex) {
        super(String.format(
                "You request to: %s, with pay load: %s, thrown exception with message: %s",
                urlRequest, tehnomirPayLoad.toString(), ex.getMessage()
        ));
    }
}
