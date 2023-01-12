package software.sigma.bu003.internship.coparts.client.technomir.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.sigma.bu003.internship.coparts.client.technomir.client.TechnomirClient;
import software.sigma.bu003.internship.coparts.client.technomir.mapper.TechnomirMapper;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.TechnomirPartWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.StockTechnomirPartWrapper;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.TechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.StockTechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.exception.TechnomirEmptyStockException;
import software.sigma.bu003.internship.coparts.client.technomir.exception.TechnomirPartNotFoundException;
import software.sigma.bu003.internship.coparts.part.entity.Part;

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

    @Mock
    private TechnomirMapper mapper;

    @InjectMocks
    private TechnomirService sut;

    private final String CODE = "7701";
    private final String BRAND = "Audi";
    private final Part TEST_PART = new Part(BRAND, CODE);

    @Test
    void shouldReturnStockPartsIfSuccessfully() {
        StockTechnomirPart stockTechnomirPart = new StockTechnomirPart();

        StockTechnomirPartWrapper stockTechnomirPartWrapper = new StockTechnomirPartWrapper();
        stockTechnomirPartWrapper.setData(List.of(stockTechnomirPart));

        when(technomirClient.getListStockParts()).thenReturn(Optional.of(List.of(stockTechnomirPart)));
        when(mapper.fromStockTechnomirPartToPart(stockTechnomirPart)).thenReturn(TEST_PART);

        sut.getAllParts();

        verify(technomirClient, times(1)).getListStockParts();
        verify(mapper, times(1)).fromStockTechnomirPartToPart(stockTechnomirPart);
    }

    @Test
    void shouldTrowExceptionIfStockPartsEmpty() {
        when(technomirClient.getListStockParts()).thenReturn(Optional.empty());

        assertThrows(TechnomirEmptyStockException.class, () -> sut.getAllParts());
        verify(technomirClient, times(1)).getListStockParts();
    }

    @Test
    void shouldReturnPartsIfSuccessfully() {
        TechnomirPart technomirPart = new TechnomirPart();

        TechnomirPartWrapper technomirPartWrapper = new TechnomirPartWrapper();
        technomirPartWrapper.setData(List.of(technomirPart));

        when(technomirClient.getPartsByCode(CODE)).thenReturn(Optional.of(List.of(technomirPart)));
        when(mapper.fromTehnomirPartToPart(technomirPart)).thenReturn(TEST_PART);

        sut.getPartByCode(CODE);

        verify(technomirClient, times(1)).getPartsByCode(CODE);
        verify(mapper, times(1)).fromTehnomirPartToPart(technomirPart);
    }

    @Test
    void shouldTrowExceptionIfPartsIsEmpty() {
        when(technomirClient.getPartsByCode(CODE)).thenReturn(Optional.empty());

        assertThrows(TechnomirPartNotFoundException.class, () -> sut.getPartByCode(CODE));
        verify(technomirClient, times(1)).getPartsByCode(CODE);
    }
}
