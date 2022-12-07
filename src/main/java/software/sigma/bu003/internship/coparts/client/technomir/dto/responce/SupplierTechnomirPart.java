package software.sigma.bu003.internship.coparts.client.technomir.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.sigma.bu003.internship.coparts.entity.Currency;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
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
