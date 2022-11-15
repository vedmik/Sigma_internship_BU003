package software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.config.ApiUri;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.config.TehnomirClientConfig;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.request.RequestBody;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.PartWrapper;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.StockPartsWrapper;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.Part;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.StockPart;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.Supplier;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.exception.TehnomirClientException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TehnomirClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TehnomirClientConfig tehnomirClientConfig;

    @InjectMocks
    private TehnomirClient client;

    private final String urlToApi = "http://test:8080";

    private final String apiToken = "12345";

    private final RequestBody requestBody = new RequestBody();

    @Test
    void getListStockParts_TakeVoid_ReturnStockParts() {
        StockPart stockPart = new StockPart();

        StockPartsWrapper stockPartsWrapper = new StockPartsWrapper();
        stockPartsWrapper.setData(List.of(stockPart));

        requestBody.setApiToken(apiToken);

        when(tehnomirClientConfig.getUrlToApi()).thenReturn(urlToApi);
        when(tehnomirClientConfig.getApiToken()).thenReturn(apiToken);
        when(restTemplate.postForObject(
                urlToApi + ApiUri.PRICE_GET_STOCK_PRICE.str,
                requestBody,
                StockPartsWrapper.class)).thenReturn(stockPartsWrapper);

        Optional<List<StockPart>> actual = client.getListStockParts();

        assertEquals(Optional.of(List.of(stockPart)), actual);
        verify(tehnomirClientConfig).getUrlToApi();
        verify(tehnomirClientConfig).getApiToken();
        verify(restTemplate).postForObject(
                urlToApi + ApiUri.PRICE_GET_STOCK_PRICE.str,
                requestBody,
                StockPartsWrapper.class);
    }

    @Test
    void getListStockParts_TakeVoid_ReturnOptionalEmpty() {
        StockPart stockPart = new StockPart();

        StockPartsWrapper stockPartsWrapper = new StockPartsWrapper();
        stockPartsWrapper.setData(List.of(stockPart));

        requestBody.setApiToken(apiToken);

        when(tehnomirClientConfig.getUrlToApi()).thenReturn(urlToApi);
        when(tehnomirClientConfig.getApiToken()).thenReturn(apiToken);
        when(restTemplate.postForObject(
                urlToApi + ApiUri.PRICE_GET_STOCK_PRICE.str,
                requestBody,
                StockPartsWrapper.class)).thenReturn(null);

        Optional<List<StockPart>> actual = client.getListStockParts();

        assertEquals(Optional.empty(), actual);
        verify(tehnomirClientConfig).getUrlToApi();
        verify(tehnomirClientConfig).getApiToken();
        verify(restTemplate).postForObject(
                urlToApi + ApiUri.PRICE_GET_STOCK_PRICE.str,
                requestBody,
                StockPartsWrapper.class);
    }

    @Test
    void getListStockParts_TakeVoid_ReturnException() {
        StockPart stockPart = new StockPart();

        StockPartsWrapper stockPartsWrapper = new StockPartsWrapper();
        stockPartsWrapper.setData(List.of(stockPart));

        requestBody.setApiToken(apiToken);

        when(tehnomirClientConfig.getUrlToApi()).thenReturn(urlToApi);
        when(tehnomirClientConfig.getApiToken()).thenReturn(apiToken);
        when(restTemplate.postForObject(
                urlToApi + ApiUri.PRICE_GET_STOCK_PRICE.str,
                requestBody,
                StockPartsWrapper.class)).thenThrow(RuntimeException.class);

        assertThrows(TehnomirClientException.class, () -> client.getListStockParts());
        verify(tehnomirClientConfig).getUrlToApi();
        verify(tehnomirClientConfig).getApiToken();
        verify(restTemplate).postForObject(
                urlToApi + ApiUri.PRICE_GET_STOCK_PRICE.str,
                requestBody,
                StockPartsWrapper.class);
    }


    @Test
    void getPartsByCode_TakeCode_ReturnPart() {
        PartWrapper partWrapper = new PartWrapper();
        Part part = new Part();
        Supplier supplier = new Supplier();
        part.setRests(List.of(supplier));
        partWrapper.setData(List.of(part));

        requestBody.setApiToken(apiToken);
        String code = "7701";
        requestBody.setCode(code);

        when(tehnomirClientConfig.getUrlToApi()).thenReturn(urlToApi);
        when(tehnomirClientConfig.getApiToken()).thenReturn(apiToken);
        when(restTemplate.postForObject(
                urlToApi + ApiUri.PRICE_POSITION_SEARCH.str,
                requestBody,
                PartWrapper.class)).thenReturn(partWrapper);

        Optional<List<Part>> actual = client.getPartsByCode(code);

        assertEquals(Optional.of(List.of(part)), actual);
        verify(tehnomirClientConfig).getUrlToApi();
        verify(tehnomirClientConfig).getApiToken();
        verify(restTemplate).postForObject(
                urlToApi + ApiUri.PRICE_POSITION_SEARCH.str,
                requestBody,
                PartWrapper.class);
    }

    @Test
    void getPartsByCode_TakeCode_ReturnOptionalEmpty() {
        PartWrapper partWrapper = new PartWrapper();
        Part part = new Part();
        Supplier supplier = new Supplier();
        part.setRests(List.of(supplier));
        partWrapper.setData(List.of(part));

        requestBody.setApiToken(apiToken);
        String code = "7701";
        requestBody.setCode(code);

        when(tehnomirClientConfig.getUrlToApi()).thenReturn(urlToApi);
        when(tehnomirClientConfig.getApiToken()).thenReturn(apiToken);
        when(restTemplate.postForObject(
                urlToApi + ApiUri.PRICE_POSITION_SEARCH.str,
                requestBody,
                PartWrapper.class)).thenReturn(null);

        Optional<List<Part>> actual = client.getPartsByCode(code);

        assertEquals(Optional.empty(), actual);
        verify(tehnomirClientConfig).getUrlToApi();
        verify(tehnomirClientConfig).getApiToken();
        verify(restTemplate).postForObject(
                urlToApi + ApiUri.PRICE_POSITION_SEARCH.str,
                requestBody,
                PartWrapper.class);
    }
    @Test
    void getPartsByCode_TakeCode_ReturnException() {
        PartWrapper partWrapper = new PartWrapper();
        Part part = new Part();
        Supplier supplier = new Supplier();
        part.setRests(List.of(supplier));
        partWrapper.setData(List.of(part));

        requestBody.setApiToken(apiToken);
        String code = "7701";
        requestBody.setCode(code);

        when(tehnomirClientConfig.getUrlToApi()).thenReturn(urlToApi);
        when(tehnomirClientConfig.getApiToken()).thenReturn(apiToken);
        when(restTemplate.postForObject(
                urlToApi + ApiUri.PRICE_POSITION_SEARCH.str,
                requestBody,
                PartWrapper.class)).thenThrow(RuntimeException.class);

        assertThrows(TehnomirClientException.class, () -> client.getPartsByCode(code));
        verify(tehnomirClientConfig).getUrlToApi();
        verify(tehnomirClientConfig).getApiToken();
        verify(restTemplate).postForObject(
                urlToApi + ApiUri.PRICE_POSITION_SEARCH.str,
                requestBody,
                PartWrapper.class);
    }
}
