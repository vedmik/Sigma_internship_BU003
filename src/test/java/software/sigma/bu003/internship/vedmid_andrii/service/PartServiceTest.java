package software.sigma.bu003.internship.vedmid_andrii.service;

import org.junit.jupiter.api.Test;
import software.sigma.bu003.internship.vedmid_andrii.entity.Part;
import software.sigma.bu003.internship.vedmid_andrii.exception.PartNotFoundException;
import software.sigma.bu003.internship.vedmid_andrii.repository.PartRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PartServiceTest {

    private final PartRepository repository = mock(PartRepository.class);

    private final PartService service = new PartService(repository);

    private final String partId = "1111111111111111";
    private final String brand = "Audi";
    private final String code ="vw12345";
    private final Part part = Part.builder()
            .id(partId)
            .brand(brand)
            .code(code)
            .price(11.11)
            .description("Some description")
            .build();
    private final Part partWithoutBrand = Part.builder().code(code).build();
    private final Part partWithoutCode = Part.builder().brand(brand).build();

    private final String message404 = "This part is not available in the DB";
    private final String messageWrongPart = "Missing brand or part code";

    @Test
    void createPart_TakePartAndTakeFromDBThisPart_ReturnExistingPart() {
        when(repository.findByBrandAndCode(brand, code)).thenReturn(part);

        Part actual = service.createPart(part);

        assertEquals(part, actual);
        verify(repository).findByBrandAndCode(brand, code);
    }

    @Test
    void createPart_TakePartAndTakeFromDBAnotherPart_ReturnNewPart() {
        when(repository.findByBrandAndCode(brand, code)).thenReturn(null);
        when(repository.save(part)).thenReturn(part);

        Part actual = service.createPart(part);

        assertEquals(part, actual);
        verify(repository).findByBrandAndCode(brand, code);
        verify(repository).save(part);
    }

    @Test
    void createPart_TakePartWithoutBrand_ReturnException() {
        PartNotFoundException thrown = assertThrows(PartNotFoundException.class, () ->
                service.createPart(partWithoutBrand)
        );
        assertEquals(messageWrongPart, thrown.getMessage());
    }

    @Test
    void createPart_TakePartWithoutCode_ReturnException() {
        PartNotFoundException thrown = assertThrows(PartNotFoundException.class, () ->
                service.createPart(partWithoutCode)
        );
        assertEquals(messageWrongPart, thrown.getMessage());
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
        when(repository.findByBrandAndCode(brand, code)).thenReturn(part);

        Part actual = service.getPart(brand, code);

        assertEquals(part, actual);
        verify(repository).findByBrandAndCode(brand, code);
    }

    @Test
    void getPart_TakeEmptyStringBrandOrEmptyStringCode_ReturnPart() {
        when(repository.findByBrandAndCode("", "")).thenReturn(null);

        PartNotFoundException thrown = assertThrows(PartNotFoundException.class, () ->
            service.getPart("", "")
        );
        assertEquals(message404, thrown.getMessage());
        verify(repository).findByBrandAndCode("", "");
    }

    @Test
    void updatePart_TakePartWithAnotherPriceAndAnotherDescription_ReturnUpdatedPart() {
        Part newPartData = Part.builder()
                .id(partId)
                .brand(brand)
                .code(code)
                .price(300D)
                .description("111")
                .build();

        when(repository.findByBrandAndCode(brand, code)).thenReturn(part);
        when(repository.save(newPartData)).thenReturn(newPartData);

        Part actual = service.updatePart(newPartData);

        assertEquals(newPartData, actual);
        verify(repository).findByBrandAndCode(brand, code);
        verify(repository).save(newPartData);
    }

    @Test
    void updatePart_TakePartWithAnotherPriceAndWithoutDesc_ReturnUpdatedPart() {
        Part partDTOWithoutDesc = Part.builder()
                .id(partId)
                .brand(brand)
                .code(code)
                .price(300D)
                .build();

        Part updatedPartWithDesc = Part.builder()
                .id(partId)
                .brand(brand)
                .code(code)
                .price(300D)
                .description("Some description")
                .build();

        when(repository.findByBrandAndCode(brand, code)).thenReturn(part);
        when(repository.save(updatedPartWithDesc)).thenReturn(updatedPartWithDesc);

        Part actual = service.updatePart(partDTOWithoutDesc);

        assertEquals(updatedPartWithDesc, actual);
        verify(repository).findByBrandAndCode(brand, code);
        verify(repository).save(updatedPartWithDesc);
    }

    @Test
    void updatePart_TakePartWithAnotherDescAndWithoutPrice_ReturnUpdatedPart() {
        Part partDTOWithoutPrice = Part.builder()
                .id(partId)
                .brand(brand)
                .code(code)
                .description("another description")
                .build();

        Part updatedPartWithPrice = Part.builder()
                .id(partId)
                .brand(brand)
                .code(code)
                .price(11.11)
                .description("another description")
                .build();

        when(repository.findByBrandAndCode(brand, code)).thenReturn(part);
        when(repository.save(updatedPartWithPrice)).thenReturn(updatedPartWithPrice);

        Part actual = service.updatePart(partDTOWithoutPrice);

        assertEquals(updatedPartWithPrice, actual);
        verify(repository).findByBrandAndCode(brand, code);
        verify(repository).save(updatedPartWithPrice);
    }

    @Test
    void updatePart_TakePartWithEmptyBrand_ReturnException() {
        PartNotFoundException thrown = assertThrows(PartNotFoundException.class, () ->
                service.updatePart(partWithoutBrand)
        );
        assertEquals(messageWrongPart, thrown.getMessage());
    }

    @Test
    void updatePart_TakePartWithEmptyCode_ReturnException() {
        PartNotFoundException thrown = assertThrows(PartNotFoundException.class, () ->
                service.updatePart(partWithoutCode)
        );
        assertEquals(messageWrongPart, thrown.getMessage());
    }

    @Test
    void updatePart_TakeWrongPart_ReturnException() {
        when(repository.findByBrandAndCode(brand, code)).thenReturn(null);

        PartNotFoundException thrown = assertThrows(PartNotFoundException.class, () ->
                service.updatePart(part)
        );
        assertEquals(message404, thrown.getMessage());
        verify(repository).findByBrandAndCode(brand, code);
    }

    @Test
    void deletePart_TakePart_ReturnVoid() {
        when(repository.findByBrandAndCode(brand, code)).thenReturn(part);
        doNothing().when(repository).delete(part);

        service.deletePart(brand, code);

        verify(repository).findByBrandAndCode(brand, code);
        verify(repository).delete(part);
    }

    @Test
    void deletePart_TakeWrongPart_ReturnException() {
        when(repository.findByBrandAndCode(brand, code)).thenReturn(null);

        PartNotFoundException thrown = assertThrows(PartNotFoundException.class, () ->
                service.deletePart(brand, code)
        );
        assertEquals(message404, thrown.getMessage());
        verify(repository).findByBrandAndCode(brand, code);
    }
}