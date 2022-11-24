package software.sigma.bu003.internship.coparts.client.technomir.entity.responce;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TechnomirPart {
    private int productId;
    private int brandId;
    private String brand;
    private String code;
    private String descriptionRus;
    private String weight;
    private int isExistProductInfo;
    private List<SupplierTehnomirPart> rests;

    @Data
    @NoArgsConstructor
    public class SupplierTehnomirPart {
        private String priceLogo;
        private String price;
        private String currency;
        private int quantity;
        private String quantityType;
        private int multiplicity;
        private String priceQuality;
        private int deliveryTypeId;
        private String deliveryType;
        private int deliveryTime;
        private String deliveryDate;
        private int deliveryPercent;
        private String priceChangeDate;
        private int isReturn;
        private int isPriceFinal;
    }
}
