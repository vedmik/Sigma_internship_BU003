package software.sigma.bu003.internship.coparts.client.technomir.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TechnomirPart {
    private int productId;
    private int brandId;
    private String brand;
    private String code;
    private String descriptionRus;
    private String weight;
    private int isExistProductInfo;
    private List<SupplierTechnomirPart> rests;
}
