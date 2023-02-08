package software.sigma.bu003.internship.coparts.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import software.sigma.bu003.internship.coparts.part.entity.Part;
import software.sigma.bu003.internship.coparts.user.model.User;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Order {

    @Id
    private String id;
    private OrderStatus orderStatus;
    private User user;
    private List<Part> parts;
}
