package software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.config.ApiUri;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.request.RequestBody;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.config.TehnomirClientConfig;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.Part;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.PartWrapper;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.StockPart;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.StockPartsWrapper;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.exception.TehnomirClientException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TehnomirClient {

    private final RestTemplate restTemplate;

    private final TehnomirClientConfig tehnomirClientConfig;

    public Optional<List<StockPart>> getListStockParts() {
        RequestBody requestBody = new RequestBody();
        requestBody.setApiToken(tehnomirClientConfig.getApiToken());

        String urlRequest = tehnomirClientConfig.getUrlToApi() + ApiUri.PRICE_GET_STOCK_PRICE.str;

        try {
            StockPartsWrapper exchange = restTemplate.postForObject(
                    urlRequest,
                    requestBody,
                    StockPartsWrapper.class);

            return Objects.isNull(exchange)
                    ? Optional.empty()
                    : Optional.of(exchange.getData());

        } catch (RuntimeException ex) {
            throw new TehnomirClientException(ex);
        }
    }

    public Optional<List<Part>> getPartsByCode(String code) {
        RequestBody requestBody = new RequestBody();
        requestBody.setCode(code);
        requestBody.setApiToken(tehnomirClientConfig.getApiToken());

        String urlRequest = tehnomirClientConfig.getUrlToApi() + ApiUri.PRICE_POSITION_SEARCH.str;

        try {
            PartWrapper exchange = restTemplate.postForObject(
                    urlRequest,
                    requestBody,
                    PartWrapper.class);

            return Objects.isNull(exchange)
                    ? Optional.empty()
                    : Optional.of(exchange.getData());

        } catch (RuntimeException ex) {
            throw new TehnomirClientException(ex);
        }
    }
}
