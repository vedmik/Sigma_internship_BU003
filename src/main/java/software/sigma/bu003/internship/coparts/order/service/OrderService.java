package software.sigma.bu003.internship.coparts.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.coparts.order.model.Order;
import software.sigma.bu003.internship.coparts.order.model.OrderStatus;
import software.sigma.bu003.internship.coparts.order.repository.OrderRepository;
import software.sigma.bu003.internship.coparts.order.service.exception.OrderHasNotCreationException;
import software.sigma.bu003.internship.coparts.order.service.exception.OrderIsNotActiveException;
import software.sigma.bu003.internship.coparts.order.service.exception.OrderNotFoundException;
import software.sigma.bu003.internship.coparts.part.entity.Part;
import software.sigma.bu003.internship.coparts.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    
    public List<Order> getAllOrders() {
        return orderRepository.findOrdersByUser(User.getCurrentUser());
    }

    public Order getOrder(String orderID) {
        return orderRepository.findOrderByIdAndUser(orderID, User.getCurrentUser())
                .orElseThrow(() -> new OrderNotFoundException(orderID));
    }

    public Order createNewOrder(List<Part> parts) {
        Order order = Order.builder()
                .user(User.getCurrentUser())
                .orderStatus(OrderStatus.ACTIVE)
                .parts(parts)
                .build();
        return orderRepository.save(order);
    }

    public Order updateOrder(String orderID, List<Part> parts) {
        Order order = orderRepository.findById(orderID)
                .orElseThrow(() -> new OrderHasNotCreationException(User.getCurrentUser()));
        if(order.getOrderStatus() != OrderStatus.ACTIVE) {
            throw new OrderIsNotActiveException(orderID, order.getOrderStatus());
        }
        order.setParts(parts);

        return orderRepository.save(order);
    }

    public void deleteOrder(String orderID) {
        Order order = orderRepository.findById(orderID)
                .orElseThrow(() -> new OrderHasNotCreationException(User.getCurrentUser()));

        order.setOrderStatus(OrderStatus.CANCELED);
    }

    public void placeOrder(String orderID) {
        Order order = orderRepository.findById(orderID)
                .orElseThrow(() -> new OrderHasNotCreationException(User.getCurrentUser()));

        order.setOrderStatus(OrderStatus.PLACED);
    }
}
