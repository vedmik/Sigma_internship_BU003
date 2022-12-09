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
import software.sigma.bu003.internship.coparts.client.technomir.config.TechnomirClientConfig;
import software.sigma.bu003.internship.coparts.client.technomir.dto.request.TechnomirPayLoad;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.TechnomirPartWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.StockTechnomirPartWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.TechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.StockTechnomirPart;
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
    private TechnomirClientConfig technomirClientConfig;

    @InjectMocks
    private TechnomirClient sut;

    private final String URL_TO_API = "http://test:8080";

    private final String PART_CODE = "7701";

    private final String API_TOKEN = "12345";

    private final TechnomirPayLoad testTechnomirPayLoad = new TechnomirPayLoad(API_TOKEN);

    private final StockTechnomirPart stockTechnomirPart = new StockTechnomirPart();

    private final StockTechnomirPartWrapper stockTechnomirPartWrapper = new StockTechnomirPartWrapper();

    private final TechnomirPartWrapper technomirPartWrapper = new TechnomirPartWrapper();
    private final TechnomirPart technomirPart = new TechnomirPart();


    @BeforeEach
    public void init(){
        stockTechnomirPartWrapper.setData(List.of(stockTechnomirPart));
        technomirPartWrapper.setData(List.of(technomirPart));

        when(technomirClientConfig.getUrlToApi()).thenReturn(URL_TO_API);
        when(technomirClientConfig.getApiToken()).thenReturn(API_TOKEN);
    }

    @Test
    void shouldReturnListStockPartsIfSuccessfully() {
        when(restTemplate.postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_GET_STOCK_PRICE.getStr(),
                testTechnomirPayLoad,
                StockTechnomirPartWrapper.class)).thenReturn(stockTechnomirPartWrapper);

        Optional<List<StockTechnomirPart>> actual = sut.getListStockParts();

        assertEquals(Optional.of(List.of(stockTechnomirPart)), actual);
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_GET_STOCK_PRICE.getStr(),
                testTechnomirPayLoad,
                StockTechnomirPartWrapper.class);
    }

    @Test
    void shouldReturnOptionalEmptyIfAPIReturnEmpty() {
        when(restTemplate.postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_GET_STOCK_PRICE.getStr(),
                testTechnomirPayLoad,
                StockTechnomirPartWrapper.class)).thenReturn(null);

        Optional<List<StockTechnomirPart>> actual = sut.getListStockParts();

        assertEquals(Optional.empty(), actual);
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_GET_STOCK_PRICE.getStr(),
                testTechnomirPayLoad,
                StockTechnomirPartWrapper.class);
    }

    @Test
    void shouldThrowExceptionIfApiWrong() {
        when(restTemplate.postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_GET_STOCK_PRICE.getStr(),
                testTechnomirPayLoad,
                StockTechnomirPartWrapper.class)).thenThrow(RestClientException.class);

        assertThrows(TechnomirClientException.class, () -> sut.getListStockParts());
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_GET_STOCK_PRICE.getStr(),
                testTechnomirPayLoad,
                StockTechnomirPartWrapper.class);
    }
    
    @Test
    void shouldReturnPartIfSuccessfully() {
        testTechnomirPayLoad.setCode(PART_CODE);

        when(restTemplate.postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_POSITION_SEARCH.getStr(),
                testTechnomirPayLoad,
                TechnomirPartWrapper.class)).thenReturn(technomirPartWrapper);

        sut.getPartsByCode(PART_CODE);

        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_POSITION_SEARCH.getStr(),
                testTechnomirPayLoad,
                TechnomirPartWrapper.class);
    }

    @Test
    void shouldReturnOptionalEmptyIfPartNotFound() {
        testTechnomirPayLoad.setCode(PART_CODE);

        when(restTemplate.postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_POSITION_SEARCH.getStr(),
                testTechnomirPayLoad,
                TechnomirPartWrapper.class)).thenReturn(null);

        Optional<List<TechnomirPart>> actual = sut.getPartsByCode(PART_CODE);

        assertEquals(Optional.empty(), actual);
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_POSITION_SEARCH.getStr(),
                testTechnomirPayLoad,
                TechnomirPartWrapper.class);
    }

    @Test
    void shouldThrowExceptionIfApiWasWrong() {
        testTechnomirPayLoad.setCode(PART_CODE);

        when(restTemplate.postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_POSITION_SEARCH.getStr(),
                testTechnomirPayLoad,
                TechnomirPartWrapper.class)).thenThrow(RestClientException.class);

        assertThrows(TechnomirClientException.class, () -> sut.getPartsByCode(PART_CODE));
        verify(restTemplate, times(1)).postForObject(
                URL_TO_API + TechnomirApiUri.PRICE_POSITION_SEARCH.getStr(),
                testTechnomirPayLoad,
                TechnomirPartWrapper.class);
    }
}
