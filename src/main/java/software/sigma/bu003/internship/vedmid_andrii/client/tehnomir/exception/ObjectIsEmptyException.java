package software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.exception;

public class ObjectIsEmptyException extends RuntimeException {
    public ObjectIsEmptyException() {
        super("The Object is not provided");
    }
}
