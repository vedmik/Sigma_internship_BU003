package software.sigma.bu003.internship.vedmid_andrii.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartDTO {
    private String brand;
    private String code;
    private Double price;
    private String description;
}
