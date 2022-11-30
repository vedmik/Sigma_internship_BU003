package software.sigma.bu003.internship.coparts.client.technomir.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import software.sigma.bu003.internship.coparts.client.technomir.config.TechnomirApiUri;
import software.sigma.bu003.internship.coparts.client.technomir.dto.request.TechnomirPayLoad;
import software.sigma.bu003.internship.coparts.client.technomir.config.TechnomirClientConfig;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.TechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.TechnomirPartWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.StockTechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.StockTechnomirPartWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.exception.TechnomirClientException;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TechnomirClient {

    private final RestTemplate restTemplate;

    private final TechnomirClientConfig technomirClientConfig;

    public Optional<List<StockTechnomirPart>> getListStockParts() {
        TechnomirPayLoad technomirPayLoad = new TechnomirPayLoad(technomirClientConfig.getApiToken());

        String urlRequest = technomirClientConfig.getUrlToApi() + TechnomirApiUri.PRICE_GET_STOCK_PRICE.getStr();

        StockTechnomirPartWrapper technomirResponse;
        try {
            technomirResponse = restTemplate.postForObject(
                    urlRequest,
                    technomirPayLoad,
                    StockTechnomirPartWrapper.class);

        } catch (RestClientException ex) {
            throw new TechnomirClientException(urlRequest, technomirPayLoad, ex);
        }

        return Optional.ofNullable(technomirResponse)
                .map(StockTechnomirPartWrapper::getData);
    }

    public Optional<List<TechnomirPart>> getPartsByCode(String code) {
        TechnomirPayLoad technomirPayLoad = new TechnomirPayLoad(technomirClientConfig.getApiToken());
        technomirPayLoad.setCode(code);

        String urlRequest = technomirClientConfig.getUrlToApi() + TechnomirApiUri.PRICE_POSITION_SEARCH.getStr();

        TechnomirPartWrapper technomirResponse;
        try {
            technomirResponse = restTemplate.postForObject(
                    urlRequest,
                    technomirPayLoad,
                    TechnomirPartWrapper.class);

        } catch (RestClientException ex) {
            throw new TechnomirClientException(urlRequest, technomirPayLoad, ex);
        }

        return Optional.ofNullable(technomirResponse)
                .map(TechnomirPartWrapper::getData);
    }
}
