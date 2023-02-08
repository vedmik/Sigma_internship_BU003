package software.sigma.bu003.internship.coparts.order.service.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderID) {
        super(String.format("Order with \"ID\": %s has not found!", orderID));
    }
}
