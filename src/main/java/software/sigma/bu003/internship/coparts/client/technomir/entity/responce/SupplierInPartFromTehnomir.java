package software.sigma.bu003.internship.coparts.client.technomir.entity.responce;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SupplierInPartFromTehnomir {
        private String priceLogo;
        private Double price;
        private String currency;
        private Long quantity;
        private String quantityType;
        private Integer multiplicity;
        private Double priceQuality;
        private Integer deliveryTypeId;
        private String deliveryType;
        private Integer deliveryTime;
        private String deliveryDate;
        private Integer deliveryPercent;
        private String priceChangeDate;
        private Integer isReturn;
        private Integer isPriceFinal;
}
