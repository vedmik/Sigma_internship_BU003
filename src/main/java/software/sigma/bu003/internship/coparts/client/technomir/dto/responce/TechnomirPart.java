package software.sigma.bu003.internship.coparts.client.technomir.dto.responce;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.sigma.bu003.internship.coparts.entity.Currency;

import java.time.LocalDateTime;
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
    private List<SupplierTechnomirPart> rests;

    @Data
    @NoArgsConstructor
    public class SupplierTechnomirPart {
        private String priceLogo;
        private String price;
        private Currency currency;
        private int quantity;
        private String quantityType;
        private int multiplicity;
        private String priceQuality;
        private int deliveryTypeId;
        private String deliveryType;
        private int deliveryTime;
        private LocalDateTime deliveryDate;
        private int deliveryPercent;
        private LocalDateTime priceChangeDate;
        private int isReturn;
        private int isPriceFinal;
    }
}
