package software.sigma.bu003.internship.coparts.client.technomir.entity.responce;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockTechnomirPart {

    private Long productId;
    private String brand;
    private String code;
    private String descriptionRus;
    private Integer quantity;
    private Double price;
    private String currency;
    private String codePrinted;
    private Double priceForRemote;

    public void setPrice(String price) {
        price = price.replace(",", "");
        this.price = Double.parseDouble(price);
    }

    public void setPriceForRemote(String priceForRemote) {
        priceForRemote = priceForRemote.replace(",", "");
        this.priceForRemote = Double.parseDouble(priceForRemote);
    }

}
