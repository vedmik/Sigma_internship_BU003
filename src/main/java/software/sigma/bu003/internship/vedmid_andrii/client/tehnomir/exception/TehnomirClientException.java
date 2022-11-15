package software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.exception;

public class TehnomirClientException extends RuntimeException {

    public TehnomirClientException(RuntimeException ex) {
        super(ex.getMessage());
    }
}
