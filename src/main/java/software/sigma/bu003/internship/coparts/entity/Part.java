package software.sigma.bu003.internship.coparts.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
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
    private List<Supplier> supplierList;


    @PersistenceCreator
    public Part(@NotNull String brand, @NotNull String code){
        this.brand = brand;
        this.code = code;
        this.id = brand.replaceAll("\\s","") + code;
    }

    @Data
    @NoArgsConstructor
    public static class Supplier {
        private String priceLogo;
        private String price;
        private Currency currency;
    }

}
