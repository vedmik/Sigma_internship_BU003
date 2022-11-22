package software.sigma.bu003.internship.coparts.client.technomir.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import software.sigma.bu003.internship.coparts.client.technomir.config.TechnomirApiUri;
import software.sigma.bu003.internship.coparts.client.technomir.entity.request.TehnomirPayLoad;
import software.sigma.bu003.internship.coparts.client.technomir.config.TehnomirClientConfig;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.TechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.TechnomirPartWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockTechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockTechnomirPartWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.exception.TechnomirClientException;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TechnomirClient {

    private final RestTemplate restTemplate;

    private final TehnomirClientConfig tehnomirClientConfig;

    public Optional<List<StockTechnomirPart>> getListStockParts() {
        TehnomirPayLoad tehnomirPayLoad = new TehnomirPayLoad();
        tehnomirPayLoad.setApiToken(tehnomirClientConfig.getApiToken());

        String urlRequest = tehnomirClientConfig.getUrlToApi() + TechnomirApiUri.PRICE_GET_STOCK_PRICE.str;
        StockTechnomirPartWrapper exchange;
        try {
            exchange = restTemplate.postForObject(
                    urlRequest,
                    tehnomirPayLoad,
                    StockTechnomirPartWrapper.class);

        } catch (RestClientException ex) {
            throw new TechnomirClientException(ex);
        }

        return Optional.ofNullable(exchange)
                .map(StockTechnomirPartWrapper::getData);
    }

    public Optional<List<TechnomirPart>> getPartsByCode(String code) {
        TehnomirPayLoad tehnomirPayLoad = new TehnomirPayLoad();
        tehnomirPayLoad.setCode(code);
        tehnomirPayLoad.setApiToken(tehnomirClientConfig.getApiToken());

        String urlRequest = tehnomirClientConfig.getUrlToApi() + TechnomirApiUri.PRICE_POSITION_SEARCH.str;
        TechnomirPartWrapper exchange;

        try {
            exchange = restTemplate.postForObject(
                    urlRequest,
                    tehnomirPayLoad,
                    TechnomirPartWrapper.class);

        } catch (RestClientException ex) {
            throw new TechnomirClientException(ex);
        }

        return Optional.ofNullable(exchange)
                .map(TechnomirPartWrapper::getData);
    }
}
