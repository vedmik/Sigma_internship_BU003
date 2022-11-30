package software.sigma.bu003.internship.coparts.client.technomir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.coparts.client.technomir.client.TechnomirClient;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.TechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.StockTechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.exception.TechnomirEmptyStockException;
import software.sigma.bu003.internship.coparts.client.technomir.exception.TechnomirPartNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnomirService {

    private final TechnomirClient technomirClient;

    public List<StockTechnomirPart> getStockParts() {
        return technomirClient.getListStockParts()
                .orElseThrow(TechnomirEmptyStockException::new);
    }

    public List<TechnomirPart> getPartsByCode(String code) {
        return technomirClient.getPartsByCode(code)
                .orElseThrow(() -> new TechnomirPartNotFoundException(code));
    }
}
