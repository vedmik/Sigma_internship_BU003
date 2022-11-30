package software.sigma.bu003.internship.coparts.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.sigma.bu003.internship.coparts.entity.Part;
import software.sigma.bu003.internship.coparts.service.exception.PartAlreadyCreatedException;
import software.sigma.bu003.internship.coparts.service.exception.PartNotFoundException;
import software.sigma.bu003.internship.coparts.repository.PartRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartServiceTest {

    @Mock
    private PartRepository repository;

    @InjectMocks
    private PartService serviceUnderTest;

    private final String BRAND = "Audi";
    private final String CODE = "vw12345";
    private final String PART_ID = BRAND + CODE;
    private final Part testPart = new Part(BRAND, CODE);

    @Test
    void shouldCreatePartSuccessfully() {
        when(repository.insert(testPart)).thenReturn(testPart);

        serviceUnderTest.createPart(testPart);
        
        verify(repository, times(1)).insert(testPart);
    }

    @Test
    void shouldThrowExceptionIfPartAlreadyCreated() {
        when(repository.insert(testPart)).thenThrow(new RuntimeException());

        assertThrows(PartAlreadyCreatedException.class, () -> serviceUnderTest.createPart(testPart)
        );

        verify(repository, times(1)).insert(testPart);
    }

    @Test
    void shouldReturnAllPartsSuccessfully() {
        when(repository.findAll()).thenReturn(List.of(testPart));

        serviceUnderTest.getAllParts();

        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldReturnPartByBrandAndCodeSuccessfully() {
        when(repository.findById(PART_ID)).thenReturn(Optional.of(testPart));

        Part actual = serviceUnderTest.getPart(BRAND, CODE);

        assertEquals(testPart, actual);
        verify(repository, times(1)).findById(BRAND + CODE);
    }

    @Test
    void shouldThrowExceptionIfPartNotFound() {
        when(repository.findById(PART_ID)).thenReturn(Optional.empty());

        assertThrows(PartNotFoundException.class, () -> serviceUnderTest.getPart(BRAND, CODE)
        );

        verify(repository, times(1)).findById(PART_ID);
    }

    @Test
    void shouldUpdatePartSuccessfully() {
        Part expected = new Part(BRAND, CODE);
        expected.setPrice(300D);
        expected.setDescription("111");

        when(repository.findById(PART_ID)).thenReturn(Optional.of(testPart));
        when(repository.save(expected)).thenReturn(expected);

        Part actual = serviceUnderTest.updatePart(expected);

        assertEquals(expected, actual);
        verify(repository, times(1)).findById(PART_ID);
        verify(repository, times(1)).save(expected);
    }

    @Test
    void shouldThrowExceptionIfUpdatedPartNotFound() {
        when(repository.findById(PART_ID)).thenReturn(Optional.empty());

        assertThrows(PartNotFoundException.class, () -> serviceUnderTest.updatePart(testPart)
        );

        verify(repository, times(1)).findById(PART_ID);
    }

    @Test
    void shouldDeletePartSuccessfully() {
        when(repository.findById(PART_ID)).thenReturn(Optional.of(testPart));
        doNothing().when(repository).deleteById(PART_ID);

        serviceUnderTest.deletePart(BRAND, CODE);

        verify(repository, times(1)).findById(PART_ID);
        verify(repository, times(1)).deleteById(PART_ID);
    }

    @Test
    void shouldThrowExceptionIfDeletedPartNotFound() {
        when(repository.findById(PART_ID)).thenReturn(Optional.empty());

        assertThrows(PartNotFoundException.class, () -> serviceUnderTest.deletePart(BRAND, CODE));

        verify(repository, times(1)).findById(PART_ID);
    }
}
