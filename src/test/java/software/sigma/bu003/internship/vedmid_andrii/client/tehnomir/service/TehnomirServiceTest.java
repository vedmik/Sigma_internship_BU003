package software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.client.TehnomirClient;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.PartWrapper;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.StockPartsWrapper;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.Part;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.StockPart;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.exception.ObjectIsEmptyException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TehnomirServiceTest {

    @Mock
    private TehnomirClient tehnomirClient;

    @InjectMocks
    private TehnomirService service;

    @Test
    void getListStockParts_TakeVoid_ReturnListStockParts() {
        StockPart stockPart = new StockPart();

        StockPartsWrapper stockPartsWrapper = new StockPartsWrapper();
        stockPartsWrapper.setData(List.of(stockPart));

        when(tehnomirClient.getListStockParts()).thenReturn(Optional.of(List.of(stockPart)));

        List<StockPart> actual = service.getStockParts();

        assertEquals(List.of(stockPart), actual);
        verify(tehnomirClient).getListStockParts();
    }

    @Test
    void getListStockParts_TakeVoid_ReturnException() {
        when(tehnomirClient.getListStockParts()).thenReturn(Optional.empty());

        assertThrows(ObjectIsEmptyException.class, () -> service.getStockParts());
        verify(tehnomirClient).getListStockParts();
    }

    @Test
    void getPart_TakeCode_ReturnListPart() {
        String code = "7701";

        Part part = new Part();

        PartWrapper partWrapper = new PartWrapper();
        partWrapper.setData(List.of(part));

        when(tehnomirClient.getPartsByCode(code)).thenReturn(Optional.of(List.of(part)));

        List<Part> actual = service.getPartsByCode(code);

        assertEquals(List.of(part), actual);
        verify(tehnomirClient).getPartsByCode(code);
    }

    @Test
    void getPart_TakeCode_ReturnException() {
        String code = "7701";

        when(tehnomirClient.getPartsByCode(code)).thenReturn(Optional.empty());

        assertThrows(ObjectIsEmptyException.class, () -> service.getPartsByCode(code));
        verify(tehnomirClient).getPartsByCode(code);
    }
}