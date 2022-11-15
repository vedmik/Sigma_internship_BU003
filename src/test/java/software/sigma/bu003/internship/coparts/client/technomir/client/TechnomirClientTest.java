package software.sigma.bu003.internship.coparts.client.technomir.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import software.sigma.bu003.internship.coparts.client.technomir.config.ApiUri;
import software.sigma.bu003.internship.coparts.client.technomir.config.TehnomirClientConfig;
import software.sigma.bu003.internship.coparts.client.technomir.entity.request.RequestBody;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.PartFromTechnomirWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockPartFromTechnomirWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.PartFromTechnomir;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockPartFromTechnomir;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.SupplierInPartFromTehnomir;
import software.sigma.bu003.internship.coparts.client.technomir.exception.TehnomirClientException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnomirClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TehnomirClientConfig tehnomirClientConfig;

    @InjectMocks
    private TehnomirClient client;

    private final String URL_TO_API = "http://test:8080";

    private final String API_TOKEN = "12345";

    private final RequestBody testRequestBody = new RequestBody();

    @Test
    void shouldReturnListStockPartsIfSuccessfully() {
        StockPartFromTechnomir stockPartFromTechnomir = new StockPartFromTechnomir();

        StockPartFromTechnomirWrapper stockPartFromTechnomirWrapper = new StockPartFromTechnomirWrapper();
        stockPartFromTechnomirWrapper.setData(List.of(stockPartFromTechnomir));

        testRequestBody.setApiToken(API_TOKEN);

        when(tehnomirClientConfig.getUrlToApi()).thenReturn(URL_TO_API);
        when(tehnomirClientConfig.getApiToken()).thenReturn(API_TOKEN);
        when(restTemplate.postForObject(
                URL_TO_API + ApiUri.PRICE_GET_STOCK_PRICE.str,
                testRequestBody,
                StockPartFromTechnomirWrapper.class)).thenReturn(stockPartFromTechnomirWrapper);

        Optional<List<StockPartFromTechnomir>> actual = client.getListStockParts();

        assertEquals(Optional.of(List.of(stockPartFromTechnomir)), actual);
        verify(tehnomirClientConfig, times(1)).getUrlToApi();
        verify(tehnomirClientConfig, times(1)).getApiToken();
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + ApiUri.PRICE_GET_STOCK_PRICE.str,
                testRequestBody,
                StockPartFromTechnomirWrapper.class);
    }

    @Test
    void shouldReturnOptionalEmptyIfAPIReturnEmpty() {
        StockPartFromTechnomir stockPartFromTechnomir = new StockPartFromTechnomir();

        StockPartFromTechnomirWrapper stockPartFromTechnomirWrapper = new StockPartFromTechnomirWrapper();
        stockPartFromTechnomirWrapper.setData(List.of(stockPartFromTechnomir));

        testRequestBody.setApiToken(API_TOKEN);

        when(tehnomirClientConfig.getUrlToApi()).thenReturn(URL_TO_API);
        when(tehnomirClientConfig.getApiToken()).thenReturn(API_TOKEN);
        when(restTemplate.postForObject(
                URL_TO_API + ApiUri.PRICE_GET_STOCK_PRICE.str,
                testRequestBody,
                StockPartFromTechnomirWrapper.class)).thenReturn(null);

        Optional<List<StockPartFromTechnomir>> actual = client.getListStockParts();

        assertEquals(Optional.empty(), actual);
        verify(tehnomirClientConfig, times(1)).getUrlToApi();
        verify(tehnomirClientConfig, times(1)).getApiToken();
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + ApiUri.PRICE_GET_STOCK_PRICE.str,
                testRequestBody,
                StockPartFromTechnomirWrapper.class);
    }

    @Test
    void shouldThrowExceptionIfApiWrong() {
        StockPartFromTechnomir stockPartFromTechnomir = new StockPartFromTechnomir();

        StockPartFromTechnomirWrapper stockPartFromTechnomirWrapper = new StockPartFromTechnomirWrapper();
        stockPartFromTechnomirWrapper.setData(List.of(stockPartFromTechnomir));

        testRequestBody.setApiToken(API_TOKEN);

        when(tehnomirClientConfig.getUrlToApi()).thenReturn(URL_TO_API);
        when(tehnomirClientConfig.getApiToken()).thenReturn(API_TOKEN);
        when(restTemplate.postForObject(
                URL_TO_API + ApiUri.PRICE_GET_STOCK_PRICE.str,
                testRequestBody,
                StockPartFromTechnomirWrapper.class)).thenThrow(RuntimeException.class);

        assertThrows(TehnomirClientException.class, () -> client.getListStockParts());
        verify(tehnomirClientConfig, times(1)).getUrlToApi();
        verify(tehnomirClientConfig, times(1)).getApiToken();
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + ApiUri.PRICE_GET_STOCK_PRICE.str,
                testRequestBody,
                StockPartFromTechnomirWrapper.class);
    }


    @Test
    void shouldReturnPartIfSuccessfully() {
        PartFromTechnomirWrapper partFromTechnomirWrapper = new PartFromTechnomirWrapper();
        PartFromTechnomir partFromTechnomir = new PartFromTechnomir();
        SupplierInPartFromTehnomir supplierInPartFromTehnomir = new SupplierInPartFromTehnomir();
        partFromTechnomir.setRests(List.of(supplierInPartFromTehnomir));
        partFromTechnomirWrapper.setData(List.of(partFromTechnomir));

        testRequestBody.setApiToken(API_TOKEN);
        String code = "7701";
        testRequestBody.setCode(code);

        when(tehnomirClientConfig.getUrlToApi()).thenReturn(URL_TO_API);
        when(tehnomirClientConfig.getApiToken()).thenReturn(API_TOKEN);
        when(restTemplate.postForObject(
                URL_TO_API + ApiUri.PRICE_POSITION_SEARCH.str,
                testRequestBody,
                PartFromTechnomirWrapper.class)).thenReturn(partFromTechnomirWrapper);

        Optional<List<PartFromTechnomir>> actual = client.getPartsByCode(code);

        assertEquals(Optional.of(List.of(partFromTechnomir)), actual);
        verify(tehnomirClientConfig, times(1)).getUrlToApi();
        verify(tehnomirClientConfig, times(1)).getApiToken();
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + ApiUri.PRICE_POSITION_SEARCH.str,
                testRequestBody,
                PartFromTechnomirWrapper.class);
    }

    @Test
    void shouldReturnOptionalEmptyIfPartNotFound() {
        PartFromTechnomirWrapper partFromTechnomirWrapper = new PartFromTechnomirWrapper();
        PartFromTechnomir partFromTechnomir = new PartFromTechnomir();
        SupplierInPartFromTehnomir supplierInPartFromTehnomir = new SupplierInPartFromTehnomir();
        partFromTechnomir.setRests(List.of(supplierInPartFromTehnomir));
        partFromTechnomirWrapper.setData(List.of(partFromTechnomir));

        testRequestBody.setApiToken(API_TOKEN);
        String code = "7701";
        testRequestBody.setCode(code);

        when(tehnomirClientConfig.getUrlToApi()).thenReturn(URL_TO_API);
        when(tehnomirClientConfig.getApiToken()).thenReturn(API_TOKEN);
        when(restTemplate.postForObject(
                URL_TO_API + ApiUri.PRICE_POSITION_SEARCH.str,
                testRequestBody,
                PartFromTechnomirWrapper.class)).thenReturn(null);

        Optional<List<PartFromTechnomir>> actual = client.getPartsByCode(code);

        assertEquals(Optional.empty(), actual);
        verify(tehnomirClientConfig, times(1)).getUrlToApi();
        verify(tehnomirClientConfig, times(1)).getApiToken();
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + ApiUri.PRICE_POSITION_SEARCH.str,
                testRequestBody,
                PartFromTechnomirWrapper.class);
    }
    @Test
    void shouldThrowExceptionIfApiWasWrong() {
        PartFromTechnomirWrapper partFromTechnomirWrapper = new PartFromTechnomirWrapper();
        PartFromTechnomir partFromTechnomir = new PartFromTechnomir();
        SupplierInPartFromTehnomir supplierInPartFromTehnomir = new SupplierInPartFromTehnomir();
        partFromTechnomir.setRests(List.of(supplierInPartFromTehnomir));
        partFromTechnomirWrapper.setData(List.of(partFromTechnomir));

        testRequestBody.setApiToken(API_TOKEN);
        String code = "7701";
        testRequestBody.setCode(code);

        when(tehnomirClientConfig.getUrlToApi()).thenReturn(URL_TO_API);
        when(tehnomirClientConfig.getApiToken()).thenReturn(API_TOKEN);
        when(restTemplate.postForObject(
                URL_TO_API + ApiUri.PRICE_POSITION_SEARCH.str,
                testRequestBody,
                PartFromTechnomirWrapper.class)).thenThrow(RuntimeException.class);

        assertThrows(TehnomirClientException.class, () -> client.getPartsByCode(code));
        verify(tehnomirClientConfig, times(1)).getUrlToApi();
        verify(tehnomirClientConfig, times(1)).getApiToken();
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + ApiUri.PRICE_POSITION_SEARCH.str,
                testRequestBody,
                PartFromTechnomirWrapper.class);
    }
}
