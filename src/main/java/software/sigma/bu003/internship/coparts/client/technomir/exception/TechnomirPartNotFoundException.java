package software.sigma.bu003.internship.coparts.client.technomir.exception;

public class TechnomirPartNotFoundException extends RuntimeException {
    public TechnomirPartNotFoundException(String code) {
        super("The Part with code: " + code + " is not provided");
    }
}
