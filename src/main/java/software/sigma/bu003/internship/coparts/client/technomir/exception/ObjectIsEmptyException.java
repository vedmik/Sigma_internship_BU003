package software.sigma.bu003.internship.coparts.client.technomir.exception;

public class ObjectIsEmptyException extends RuntimeException {
    public ObjectIsEmptyException() {
        super("The Object is not provided");
    }
}
