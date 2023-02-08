package software.sigma.bu003.internship.coparts.order.service.exception;

import software.sigma.bu003.internship.coparts.order.model.OrderStatus;

public class OrderIsNotActiveException extends RuntimeException {
    public OrderIsNotActiveException(String orderID, OrderStatus orderStatus) {
        super(String.format("Order with \"ID\": %s is not ACTIVE, it has status %s!", orderID, orderStatus));
    }
}
