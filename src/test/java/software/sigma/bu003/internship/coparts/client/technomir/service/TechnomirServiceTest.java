package software.sigma.bu003.internship.coparts.client.technomir.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.sigma.bu003.internship.coparts.client.technomir.client.TehnomirClient;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.PartFromTechnomirWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockPartFromTechnomirWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.PartFromTechnomir;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockPartFromTechnomir;
import software.sigma.bu003.internship.coparts.client.technomir.exception.ObjectIsEmptyException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnomirServiceTest {

    @Mock
    private TehnomirClient tehnomirClient;

    @InjectMocks
    private TehnomirService service;

    private final String CODE = "7701";

    @Test
    void shouldReturnStockPartsIfSuccessfully() {
        StockPartFromTechnomir stockPartFromTechnomir = new StockPartFromTechnomir();

        StockPartFromTechnomirWrapper stockPartFromTechnomirWrapper = new StockPartFromTechnomirWrapper();
        stockPartFromTechnomirWrapper.setData(List.of(stockPartFromTechnomir));

        when(tehnomirClient.getListStockParts()).thenReturn(Optional.of(List.of(stockPartFromTechnomir)));

        List<StockPartFromTechnomir> actual = service.getStockParts();

        assertEquals(List.of(stockPartFromTechnomir), actual);
        verify(tehnomirClient, times(1)).getListStockParts();
    }

    @Test
    void shouldTrowExceptionIfStockPartsEmpty() {
        when(tehnomirClient.getListStockParts()).thenReturn(Optional.empty());

        assertThrows(ObjectIsEmptyException.class, () -> service.getStockParts());
        verify(tehnomirClient, times(1)).getListStockParts();
    }

    @Test
    void shouldReturnPartsIfSuccessfully() {
        PartFromTechnomir partFromTechnomir = new PartFromTechnomir();

        PartFromTechnomirWrapper partFromTechnomirWrapper = new PartFromTechnomirWrapper();
        partFromTechnomirWrapper.setData(List.of(partFromTechnomir));

        when(tehnomirClient.getPartsByCode(CODE)).thenReturn(Optional.of(List.of(partFromTechnomir)));

        List<PartFromTechnomir> actual = service.getPartsByCode(CODE);

        assertEquals(List.of(partFromTechnomir), actual);
        verify(tehnomirClient, times(1)).getPartsByCode(CODE);
    }

    @Test
    void shouldTrowExceptionIfPartsIsEmpty() {
        when(tehnomirClient.getPartsByCode(CODE)).thenReturn(Optional.empty());

        assertThrows(ObjectIsEmptyException.class, () -> service.getPartsByCode(CODE));
        verify(tehnomirClient, times(1)).getPartsByCode(CODE);
    }
}