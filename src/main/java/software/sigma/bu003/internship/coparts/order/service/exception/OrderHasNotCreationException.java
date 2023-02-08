package software.sigma.bu003.internship.coparts.order.service.exception;

import software.sigma.bu003.internship.coparts.user.model.User;

public class OrderHasNotCreationException extends RuntimeException {
    public OrderHasNotCreationException(User user) {
        super(String.format("Order by: %s %s has not found!", user.getFirstName(), user.getLastName()));
    }
}
