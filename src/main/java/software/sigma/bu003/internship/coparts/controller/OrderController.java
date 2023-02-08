package software.sigma.bu003.internship.coparts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.bu003.internship.coparts.order.model.Order;
import software.sigma.bu003.internship.coparts.order.service.OrderService;
import software.sigma.bu003.internship.coparts.part.entity.Part;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderID}")
    public Order getOrder(@PathVariable String orderID) {
        return orderService.getOrder(orderID);
    }

    @PostMapping
    public Order createNewOrder(@RequestBody List<Part> parts) {
        return orderService.createNewOrder(parts);
    }

    @PutMapping ("/{orderID}")
    public Order updateOrder(@PathVariable String orderID, @RequestBody List<Part> parts) {
        return orderService.updateOrder(orderID, parts);
    }

    @DeleteMapping("/{orderID}")
    public void deleteOrder(@PathVariable String orderID) {
        orderService.deleteOrder(orderID);
    }

    @GetMapping("/{orderID}/place")
    public void passOrder(@PathVariable String orderID) {orderService.placeOrder(orderID);}
}
