package software.sigma.bu003.internship.coparts.client.technomir.dto.request;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TechnomirPayLoad {
    private final String apiToken;
    private Integer unloadId;
    private String fromDate;
    private String toDate;
    private String string;
    private Integer brandId;
    private String code;
    private Integer isShowAnalogs;
    private String currency;
    private String reference;
    private Integer orderId;
    private String orderNumber;
    private Integer basketId;
}
