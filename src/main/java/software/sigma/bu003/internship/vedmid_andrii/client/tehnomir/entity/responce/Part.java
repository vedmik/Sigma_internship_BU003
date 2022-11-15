package software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Part {
    private Long productId;
    private Long brandId;
    private String brand;
    private String code;
    private String descriptionRus;
    private Double weight;
    private Integer isExistProductInfo;
    private List<Supplier> rests;
}
