package software.sigma.bu003.internship.vedmid_andrii.controller;

import com.mongodb.MongoWriteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import software.sigma.bu003.internship.vedmid_andrii.entity.Part;
import software.sigma.bu003.internship.vedmid_andrii.service.PartService;
import software.sigma.bu003.internship.vedmid_andrii.service.exception.PartNotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({PartController.class})
@AutoConfigureMockMvc(addFilters = false)
class PartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartService service;

    private final String urlTemplate = "/parts";
    private final String urlTemplateWithBrandCode = "/parts/{brand}/{code}";
    private final String brand = "Audi";

    private final String code ="vw12345";

    private final Part part = new Part(brand, code);

    private final String contentPart = """
            {
                     "brand": "Audi",
                     "code": "vw12345"
            }
            """;

    @Test
    void createPart_TakeJsonWithPart_ReturnWithPart() throws Exception {
        when(service.createPart(part)).thenReturn(part);

        mockMvc.perform(post(urlTemplate)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(contentPart))
                .andExpect(status().isCreated())
                .andExpect(content().json(contentPart));

        verify(service).createPart(part);
    }

    @Test
    void createPart_TakeJsonWithWrongContentPart_ReturnWithPart() throws Exception {
        when(service.createPart(part)).thenThrow(ConstraintViolationException.class);

        mockMvc.perform(post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentPart))
                .andExpect(status().isBadRequest());

        verify(service).createPart(part);
    }

    @Test
    void createPart_TakeJsonWithWrongPart_ReturnWithPart() throws Exception {
        when(service.createPart(part)).thenThrow(MongoWriteException.class);

        mockMvc.perform(post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentPart))
                .andExpect(status().isAccepted());

        verify(service).createPart(part);
    }

    @Test
    void getAllParts_TakeVoid_ReturnListParts() throws Exception {
        List<Part> expectedList = List.of(part);

        when(service.getAllParts()).thenReturn(expectedList);

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("[ %s ]", contentPart)));

        verify(service).getAllParts();
    }

    @Test
    void getPart_TakeStringBrandAndStringCode_ReturnJsonPart() throws Exception {
        when(service.getPart(brand, code)).thenReturn(part);

        mockMvc.perform(get(urlTemplateWithBrandCode, brand, code))
                .andExpect(status().isOk())
                .andExpect(content().json(contentPart));

        verify(service).getPart(brand, code);
    }

    @Test
    void getPart_TakeWrongStringBrandOrWrongStringCode_ReturnException() throws Exception {
        when(service.getPart(brand, code)).thenThrow(PartNotFoundException.class);

        mockMvc.perform(get(urlTemplateWithBrandCode, brand, code))
                .andExpect(status().isNotFound());

        verify(service).getPart(brand, code);
    }

    @Test
    void updatePart_TakeJsonPartAndSaveToDB_ReturnJsonPart() throws Exception {
        when(service.updatePart(part)).thenReturn(part);

        mockMvc.perform(put(urlTemplateWithBrandCode, brand, code)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentPart))
                .andExpect(status().isOk())
                .andExpect(content().json(contentPart));

        verify(service).updatePart(part);
    }

    @Test
    void updatePart_TakeWrongJsonPart_ReturnException() throws Exception {
        when(service.updatePart(part)).thenThrow(PartNotFoundException.class);

        mockMvc.perform(put(urlTemplateWithBrandCode, brand, code)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentPart))
                .andExpect(status().isNotFound());

        verify(service).updatePart(part);
    }

    @Test
    void deletePart_TakeStringBrandAndStringCode_ReturnVoid() throws Exception {
        doNothing().when(service).deletePart(brand, code);

        mockMvc.perform(delete(urlTemplateWithBrandCode, brand, code))
                .andExpect(status().isOk());

        verify(service).deletePart(brand, code);
    }

    @Test
    void deletePart_TakeWrongStringBrandOrWrongStringCode_ReturnVoid() throws Exception {
        doThrow(PartNotFoundException.class).when(service).deletePart(brand, code);

        mockMvc.perform(delete(urlTemplateWithBrandCode, brand, code))
                .andExpect(status().isNotFound());

        verify(service).deletePart(brand, code);
    }
}
