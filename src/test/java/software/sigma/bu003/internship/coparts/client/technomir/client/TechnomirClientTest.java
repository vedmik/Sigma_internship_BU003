package software.sigma.bu003.internship.coparts.client.technomir.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import software.sigma.bu003.internship.coparts.client.technomir.config.TechnomirApiUri;
import software.sigma.bu003.internship.coparts.client.technomir.config.TehnomirClientConfig;
import software.sigma.bu003.internship.coparts.client.technomir.entity.request.TehnomirPayLoad;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.TechnomirPartWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockTechnomirPartWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.TechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockTechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.SupplierTehnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.exception.TechnomirClientException;

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
    private TechnomirClient SUT;

    private final String URL_TO_API = "http://test:8080";

    private final String PART_CODE = "7701";

    private final TehnomirPayLoad testTehnomirPayLoad = new TehnomirPayLoad();

    private final StockTechnomirPart stockTechnomirPart = new StockTechnomirPart();

    private final StockTechnomirPartWrapper stockTechnomirPartWrapper = new StockTechnomirPartWrapper();

    private final TechnomirPartWrapper technomirPartWrapper = new TechnomirPartWrapper();
    private final TechnomirPart technomirPart = new TechnomirPart();
    private final SupplierTehnomirPart supplierTehnomirPart = new SupplierTehnomirPart();

    @BeforeEach
    public void init(){
        stockTechnomirPartWrapper.setData(List.of(stockTechnomirPart));
        technomirPart.setRests(List.of(supplierTehnomirPart));
        technomirPartWrapper.setData(List.of(technomirPart));

        String API_TOKEN = "12345";
        testTehnomirPayLoad.setApiToken(API_TOKEN);

        when(tehnomirClientConfig.getUrlToApi()).thenReturn(URL_TO_API);
        when(tehnomirClientConfig.getApiToken()).thenReturn(API_TOKEN);
    }

    @Test
    void shouldReturnListStockPartsIfSuccessfully() {
        when(restTemplate.postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_GET_STOCK_PRICE.str,
                testTehnomirPayLoad,
                StockTechnomirPartWrapper.class)).thenReturn(stockTechnomirPartWrapper);

        Optional<List<StockTechnomirPart>> actual = SUT.getListStockParts();

        assertEquals(Optional.of(List.of(stockTechnomirPart)), actual);
        verify(tehnomirClientConfig, times(1)).getUrlToApi();
        verify(tehnomirClientConfig, times(1)).getApiToken();
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_GET_STOCK_PRICE.str,
                testTehnomirPayLoad,
                StockTechnomirPartWrapper.class);
    }

    @Test
    void shouldReturnOptionalEmptyIfAPIReturnEmpty() {
        when(restTemplate.postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_GET_STOCK_PRICE.str,
                testTehnomirPayLoad,
                StockTechnomirPartWrapper.class)).thenReturn(null);

        Optional<List<StockTechnomirPart>> actual = SUT.getListStockParts();

        assertEquals(Optional.empty(), actual);
        verify(tehnomirClientConfig, times(1)).getUrlToApi();
        verify(tehnomirClientConfig, times(1)).getApiToken();
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_GET_STOCK_PRICE.str,
                testTehnomirPayLoad,
                StockTechnomirPartWrapper.class);
    }

    @Test
    void shouldThrowExceptionIfApiWrong() {
        when(restTemplate.postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_GET_STOCK_PRICE.str,
                testTehnomirPayLoad,
                StockTechnomirPartWrapper.class)).thenThrow(RestClientException.class);

        assertThrows(TechnomirClientException.class, () -> SUT.getListStockParts());
        verify(tehnomirClientConfig, times(1)).getUrlToApi();
        verify(tehnomirClientConfig, times(1)).getApiToken();
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_GET_STOCK_PRICE.str,
                testTehnomirPayLoad,
                StockTechnomirPartWrapper.class);
    }


    @Test
    void shouldReturnPartIfSuccessfully() {
        testTehnomirPayLoad.setCode(PART_CODE);

        when(restTemplate.postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_POSITION_SEARCH.str,
                testTehnomirPayLoad,
                TechnomirPartWrapper.class)).thenReturn(technomirPartWrapper);

        Optional<List<TechnomirPart>> actual = SUT.getPartsByCode(PART_CODE);

        assertEquals(Optional.of(List.of(technomirPart)), actual);
        verify(tehnomirClientConfig, times(1)).getUrlToApi();
        verify(tehnomirClientConfig, times(1)).getApiToken();
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_POSITION_SEARCH.str,
                testTehnomirPayLoad,
                TechnomirPartWrapper.class);
    }

    @Test
    void shouldReturnOptionalEmptyIfPartNotFound() {
        testTehnomirPayLoad.setCode(PART_CODE);

        when(restTemplate.postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_POSITION_SEARCH.str,
                testTehnomirPayLoad,
                TechnomirPartWrapper.class)).thenReturn(null);

        Optional<List<TechnomirPart>> actual = SUT.getPartsByCode(PART_CODE);

        assertEquals(Optional.empty(), actual);
        verify(tehnomirClientConfig, times(1)).getUrlToApi();
        verify(tehnomirClientConfig, times(1)).getApiToken();
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_POSITION_SEARCH.str,
                testTehnomirPayLoad,
                TechnomirPartWrapper.class);
    }
    @Test
    void shouldThrowExceptionIfApiWasWrong() {
        testTehnomirPayLoad.setCode(PART_CODE);

        when(restTemplate.postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_POSITION_SEARCH.str,
                testTehnomirPayLoad,
                TechnomirPartWrapper.class)).thenThrow(RestClientException.class);

        assertThrows(TechnomirClientException.class, () -> SUT.getPartsByCode(PART_CODE));
        verify(tehnomirClientConfig, times(1)).getUrlToApi();
        verify(tehnomirClientConfig, times(1)).getApiToken();
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_POSITION_SEARCH.str,
                testTehnomirPayLoad,
                TechnomirPartWrapper.class);
    }
}
