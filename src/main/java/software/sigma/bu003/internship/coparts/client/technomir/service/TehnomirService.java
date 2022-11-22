package software.sigma.bu003.internship.coparts.client.technomir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.coparts.client.technomir.client.TechnomirClient;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.TechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockTechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.exception.ObjectIsEmptyException;
import software.sigma.bu003.internship.coparts.client.technomir.exception.PartIsEmptyException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TehnomirService {

    private final TechnomirClient technomirClient;

    public List<StockTechnomirPart> getStockParts() {
        return technomirClient.getListStockParts()
                .orElseThrow(ObjectIsEmptyException::new);
    }

    public List<TechnomirPart> getPartsByCode(String code) {
        return technomirClient.getPartsByCode(code)
                .orElseThrow(() -> new PartIsEmptyException(code));
    }
}
