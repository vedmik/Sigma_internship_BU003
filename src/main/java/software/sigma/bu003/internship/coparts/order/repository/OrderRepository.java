package software.sigma.bu003.internship.coparts.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import software.sigma.bu003.internship.coparts.order.model.Order;
import software.sigma.bu003.internship.coparts.user.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findOrdersByUser(User user);

    Optional<Order> findOrderByIdAndUser(String id, User user);
}
