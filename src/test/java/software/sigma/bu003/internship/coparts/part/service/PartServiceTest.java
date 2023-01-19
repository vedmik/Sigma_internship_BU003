package software.sigma.bu003.internship.coparts.part.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.sigma.bu003.internship.coparts.client.CopartsClient;
import software.sigma.bu003.internship.coparts.part.entity.Part;
import software.sigma.bu003.internship.coparts.part.repository.PartRepository;
import software.sigma.bu003.internship.coparts.part.service.exception.PartAlreadyCreatedException;
import software.sigma.bu003.internship.coparts.part.service.exception.PartNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartServiceTest {

    @Mock
    private PartRepository partRepository;

    @Mock
    private CopartsClient copartsClient;

    @InjectMocks
    private PartService sut;

    private final String BRAND = "Audi";
    private final String CODE = "vw12345";
    private final String PART_ID = BRAND + CODE;
    private final Part testPart = new Part(BRAND, CODE);

    @Test
    void shouldCreatePartSuccessfully() {
        when(partRepository.insert(testPart)).thenReturn(testPart);

        sut.createPart(testPart);
        
        verify(partRepository, times(1)).insert(testPart);
    }

    @Test
    void shouldThrowExceptionIfPartAlreadyCreated() {
        when(partRepository.insert(testPart)).thenThrow(new RuntimeException());

        assertThrows(PartAlreadyCreatedException.class, () -> sut.createPart(testPart));

        verify(partRepository, times(1)).insert(testPart);
    }

    @Test
    void shouldReturnAllPartsSuccessfully() {
        when(partRepository.findAll()).thenReturn(List.of(testPart));

        sut.getAllParts();

        verify(partRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnPartByBrandAndCodeSuccessfully() {
        when(partRepository.findById(PART_ID)).thenReturn(Optional.of(testPart));

        Part actual = sut.getPart(BRAND, CODE);

        assertEquals(testPart, actual);
        verify(partRepository, times(1)).findById(BRAND + CODE);
    }

    @Test
    void shouldThrowExceptionIfPartNotFound() {
        when(partRepository.findById(PART_ID)).thenReturn(Optional.empty());

        assertThrows(PartNotFoundException.class, () -> sut.getPart(BRAND, CODE)
        );

        verify(partRepository, times(1)).findById(PART_ID);
    }

    @Test
    void shouldUpdatePartSuccessfully() {
        Part expected = new Part(BRAND, CODE);
        expected.setDescription("111");

        when(partRepository.findById(PART_ID)).thenReturn(Optional.of(testPart));
        when(partRepository.save(expected)).thenReturn(expected);

        Part actual = sut.updatePart(expected);

        assertEquals(expected, actual);
        verify(partRepository, times(1)).findById(PART_ID);
        verify(partRepository, times(1)).save(expected);
    }

    @Test
    void shouldThrowExceptionIfUpdatedPartNotFound() {
        when(partRepository.findById(PART_ID)).thenReturn(Optional.empty());

        assertThrows(PartNotFoundException.class, () -> sut.updatePart(testPart)
        );

        verify(partRepository, times(1)).findById(PART_ID);
    }

    @Test
    void shouldDeletePartSuccessfully() {
        when(partRepository.findById(PART_ID)).thenReturn(Optional.of(testPart));
        doNothing().when(partRepository).deleteById(PART_ID);

        sut.deletePart(BRAND, CODE);

        verify(partRepository, times(1)).findById(PART_ID);
        verify(partRepository, times(1)).deleteById(PART_ID);
    }

    @Test
    void shouldThrowExceptionIfDeletedPartNotFound() {
        when(partRepository.findById(PART_ID)).thenReturn(Optional.empty());

        assertThrows(PartNotFoundException.class, () -> sut.deletePart(BRAND, CODE));

        verify(partRepository, times(1)).findById(PART_ID);
    }

    @Test
    void shouldReturnListPartsFromDB() {
        when(copartsClient.getAllParts()).thenReturn(List.of(testPart));
        when(partRepository.saveAll(anyList())).thenReturn(List.of(testPart));

        sut.synchronizeWithTechnomir();

        verify(partRepository, times(1)).saveAll(anyList());
        verify(copartsClient, times(1)).getAllParts();
    }
}
