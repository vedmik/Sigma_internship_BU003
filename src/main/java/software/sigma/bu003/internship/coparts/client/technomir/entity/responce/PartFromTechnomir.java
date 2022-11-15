package software.sigma.bu003.internship.coparts.client.technomir.entity.responce;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PartFromTechnomir {
    private Long productId;
    private Long brandId;
    private String brand;
    private String code;
    private String descriptionRus;
    private Double weight;
    private Integer isExistProductInfo;
    private List<SupplierInPartFromTehnomir> rests;
}
