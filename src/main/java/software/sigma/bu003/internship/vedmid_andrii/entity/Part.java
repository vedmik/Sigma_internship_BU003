package software.sigma.bu003.internship.vedmid_andrii.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Part {
    @Id
    private String id;
    private String brand;
    private String code;
    private Double price;
    private String description;
}
