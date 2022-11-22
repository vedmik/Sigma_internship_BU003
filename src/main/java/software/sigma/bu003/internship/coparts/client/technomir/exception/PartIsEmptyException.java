package software.sigma.bu003.internship.coparts.client.technomir.exception;

public class PartIsEmptyException extends RuntimeException {
    public PartIsEmptyException(String code) {
        super("The Part with code: " + code + " is not provided");
    }
}
