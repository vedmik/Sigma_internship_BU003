package software.sigma.bu003.internship.coparts.client.technomir.exception;

public class TehnomirClientException extends RuntimeException {

    public TehnomirClientException(RuntimeException ex) {
        super(ex.getMessage());
    }
}
