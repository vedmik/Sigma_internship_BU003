package software.sigma.bu003.internship.coparts.client.technomir.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import software.sigma.bu003.internship.coparts.client.technomir.config.ApiUri;
import software.sigma.bu003.internship.coparts.client.technomir.entity.request.RequestBody;
import software.sigma.bu003.internship.coparts.client.technomir.config.TehnomirClientConfig;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.PartFromTechnomir;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.PartFromTechnomirWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockPartFromTechnomir;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockPartFromTechnomirWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.exception.TehnomirClientException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TehnomirClient {

    private final RestTemplate restTemplate;

    private final TehnomirClientConfig tehnomirClientConfig;

    public Optional<List<StockPartFromTechnomir>> getListStockParts() {
        RequestBody requestBody = new RequestBody();
        requestBody.setApiToken(tehnomirClientConfig.getApiToken());

        String urlRequest = tehnomirClientConfig.getUrlToApi() + ApiUri.PRICE_GET_STOCK_PRICE.str;

        try {
            StockPartFromTechnomirWrapper exchange = restTemplate.postForObject(
                    urlRequest,
                    requestBody,
                    StockPartFromTechnomirWrapper.class);

            return Objects.isNull(exchange)
                    ? Optional.empty()
                    : Optional.of(exchange.getData());

        } catch (RuntimeException ex) {
            throw new TehnomirClientException(ex);
        }
    }

    public Optional<List<PartFromTechnomir>> getPartsByCode(String code) {
        RequestBody requestBody = new RequestBody();
        requestBody.setCode(code);
        requestBody.setApiToken(tehnomirClientConfig.getApiToken());

        String urlRequest = tehnomirClientConfig.getUrlToApi() + ApiUri.PRICE_POSITION_SEARCH.str;

        try {
            PartFromTechnomirWrapper exchange = restTemplate.postForObject(
                    urlRequest,
                    requestBody,
                    PartFromTechnomirWrapper.class);

            return Objects.isNull(exchange)
                    ? Optional.empty()
                    : Optional.of(exchange.getData());

        } catch (RuntimeException ex) {
            throw new TehnomirClientException(ex);
        }
    }
}
