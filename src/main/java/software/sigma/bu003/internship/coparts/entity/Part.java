package software.sigma.bu003.internship.coparts.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;


@Data
@Document
public class Part {
    @Id
    private String id;
    @NotNull
    @Field("brand")
    private String brand;
    @NotNull
    @Field("code")
    private String code;
    @Field("price")
    private Double price;
    @Field("description")
    private String description;

    @PersistenceCreator
    public Part(@Value("brand") @NotNull String brand, @Value("code") @NotNull String code){
        this.brand = brand;
        this.code = code;
        this.id = brand + code;
    }
}
