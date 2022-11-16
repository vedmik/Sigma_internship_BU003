package software.sigma.bu003.internship.coparts.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;


@Data
@Document
public class Part {
    @Id
    private String id;
    @NotNull
    private String brand;
    @NotNull
    private String code;
    private Double price;
    private String description;

    @PersistenceCreator
    public Part(@NotNull String brand, @NotNull String code){
        this.brand = brand;
        this.code = code;
        this.id = brand + code;
    }
}
