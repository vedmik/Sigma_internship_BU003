package software.sigma.bu003.internship.vedmid_andrii.service;

import org.junit.jupiter.api.Test;
import software.sigma.bu003.internship.vedmid_andrii.entity.Part;
import software.sigma.bu003.internship.vedmid_andrii.service.exception.PartNotFoundException;
import software.sigma.bu003.internship.vedmid_andrii.repository.PartRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PartServiceTest {

    private final PartRepository repository = mock(PartRepository.class);

    private final PartService service = new PartService(repository);

    private final String brand = "Audi";
    private final String code = "vw12345";
    private final String partId = brand + code;
    private final Part part = new Part(brand, code);
    private final String message404 = String.format("Part with \"brand\": %s, \"code\": %s is not found.", brand, code);

    @Test
    void createPart_TakePartAndAddToDB_ReturnExistingPart() {
        when(repository.insert(part)).thenReturn(part);

        Part actual = service.createPart(part);

        assertEquals(part, actual);
        verify(repository).insert(part);
    }

    @Test
    void getAllParts_TakeVoid_ReturnListParts() {
        when(repository.findAll()).thenReturn(List.of(part));

        List<Part> actual = service.getAllParts();

        assertEquals(List.of(part), actual);
        verify(repository).findAll();
    }

    @Test
    void getPart_TakeStringBrandAndCode_ReturnPart() {
        when(repository.findById(partId)).thenReturn(Optional.of(part));

        Part actual = service.getPart(brand, code);

        assertEquals(part, actual);
        verify(repository).findById(brand + code);
    }

    @Test
    void getPart_TakeEmptyStringBrandOrEmptyStringCode_ReturnPart() {
        when(repository.findById(partId)).thenReturn(Optional.empty());

        PartNotFoundException thrown = assertThrows(PartNotFoundException.class, () ->
            service.getPart(brand, code)
        );
        assertEquals(message404, thrown.getMessage());
        verify(repository).findById(partId);
    }

    @Test
    void updatePart_TakePartWithAnotherPriceAndAnotherDescription_ReturnUpdatedPart() {
        Part anotherPart = new Part(brand, code);
        anotherPart.setPrice(300D);
        anotherPart.setDescription("111");

        when(repository.findById(partId)).thenReturn(Optional.of(part));
        when(repository.save(anotherPart)).thenReturn(anotherPart);

        Part actual = service.updatePart(anotherPart);

        assertEquals(anotherPart, actual);
        verify(repository).findById(partId);
        verify(repository).save(anotherPart);
    }


    @Test
    void updatePart_TakePartWithWrongId_ReturnException() {
        when(repository.findById(partId)).thenReturn(Optional.empty());

        PartNotFoundException thrown = assertThrows(PartNotFoundException.class, () ->
                service.updatePart(part)
        );
        assertEquals(message404, thrown.getMessage());
    }

    @Test
    void deletePart_TakePart_ReturnVoid() {
        when(repository.findById(partId)).thenReturn(Optional.of(part));
        doNothing().when(repository).delete(part);

        service.deletePart(brand, code);

        verify(repository).findById(partId);
        verify(repository).delete(part);
    }

    @Test
    void deletePart_TakeWrongPart_ReturnException() {
        when(repository.findById(partId)).thenReturn(Optional.empty());

        PartNotFoundException thrown = assertThrows(PartNotFoundException.class, () ->
                service.deletePart(brand, code)
        );
        assertEquals(message404, thrown.getMessage());
        verify(repository).findById(partId);
    }
}