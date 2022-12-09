package software.sigma.bu003.internship.coparts.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SupplierPart {
        private String priceLogo;
        private String price;
        private Currency currency;
}
