package software.sigma.bu003.internship.coparts.client.technomir.entity.responce;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockTechnomirPart {
    private int productId;
    private String brand;
    private String code;
    private String descriptionRus;
    private int quantity;
    private String price;
    private String currency;
    private String codePrinted;
    private String priceForRemote;
}
