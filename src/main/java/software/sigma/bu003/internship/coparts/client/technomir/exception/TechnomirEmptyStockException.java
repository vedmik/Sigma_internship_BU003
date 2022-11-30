package software.sigma.bu003.internship.coparts.client.technomir.exception;

public class TechnomirEmptyStockException extends RuntimeException {
    public TechnomirEmptyStockException() {
        super("The Object is not provided");
    }
}
