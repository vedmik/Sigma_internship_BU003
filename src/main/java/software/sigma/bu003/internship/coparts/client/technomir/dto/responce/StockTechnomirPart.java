package software.sigma.bu003.internship.coparts.client.technomir.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.sigma.bu003.internship.coparts.entity.Currency;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StockTechnomirPart {
    private int productId;
    private String brand;
    private String code;
    private String descriptionRus;
    private int quantity;
    private String price;
    private Currency currency;
    private String codePrinted;
    private String priceForRemote;
}
