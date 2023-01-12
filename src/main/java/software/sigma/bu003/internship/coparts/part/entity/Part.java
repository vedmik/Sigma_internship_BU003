package software.sigma.bu003.internship.coparts.part.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Document
public class Part {
    @Id
    private String id;
    @NotNull
    private String brand;
    @NotNull
    private String code;
    private String description;
    private List<SupplierPart> supplierList;


    @PersistenceCreator
    public Part(@NotNull String brand, @NotNull String code){
        this.brand = brand;
        this.code = code;
        this.id = brand.replaceAll("\\s","") + code;
    }
}
