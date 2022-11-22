package software.sigma.bu003.internship.coparts.client.technomir.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.sigma.bu003.internship.coparts.client.technomir.client.TechnomirClient;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.TechnomirPartWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockTechnomirPartWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.TechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockTechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.exception.ObjectIsEmptyException;
import software.sigma.bu003.internship.coparts.client.technomir.exception.PartIsEmptyException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnomirServiceTest {

    @Mock
    private TechnomirClient technomirClient;

    @InjectMocks
    private TehnomirService service;

    private final String CODE = "7701";

    @Test
    void shouldReturnStockPartsIfSuccessfully() {
        StockTechnomirPart stockTechnomirPart = new StockTechnomirPart();

        StockTechnomirPartWrapper stockTechnomirPartWrapper = new StockTechnomirPartWrapper();
        stockTechnomirPartWrapper.setData(List.of(stockTechnomirPart));

        when(technomirClient.getListStockParts()).thenReturn(Optional.of(List.of(stockTechnomirPart)));

        service.getStockParts();

        verify(technomirClient, times(1)).getListStockParts();
    }

    @Test
    void shouldTrowExceptionIfStockPartsEmpty() {
        when(technomirClient.getListStockParts()).thenReturn(Optional.empty());

        assertThrows(ObjectIsEmptyException.class, () -> service.getStockParts());
        verify(technomirClient, times(1)).getListStockParts();
    }

    @Test
    void shouldReturnPartsIfSuccessfully() {
        TechnomirPart technomirPart = new TechnomirPart();

        TechnomirPartWrapper technomirPartWrapper = new TechnomirPartWrapper();
        technomirPartWrapper.setData(List.of(technomirPart));

        when(technomirClient.getPartsByCode(CODE)).thenReturn(Optional.of(List.of(technomirPart)));

        List<TechnomirPart> actual = service.getPartsByCode(CODE);

        assertEquals(List.of(technomirPart), actual);
        verify(technomirClient, times(1)).getPartsByCode(CODE);
    }

    @Test
    void shouldTrowExceptionIfPartsIsEmpty() {
        when(technomirClient.getPartsByCode(CODE)).thenReturn(Optional.empty());

        assertThrows(PartIsEmptyException.class, () -> service.getPartsByCode(CODE));
        verify(technomirClient, times(1)).getPartsByCode(CODE);
    }
}