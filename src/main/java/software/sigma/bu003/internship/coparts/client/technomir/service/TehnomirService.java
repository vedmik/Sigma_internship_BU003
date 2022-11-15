package software.sigma.bu003.internship.coparts.client.technomir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.coparts.client.technomir.client.TehnomirClient;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.PartFromTechnomir;
import software.sigma.bu003.internship.coparts.client.technomir.entity.responce.StockPartFromTechnomir;
import software.sigma.bu003.internship.coparts.client.technomir.exception.ObjectIsEmptyException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TehnomirService {

    private final TehnomirClient tehnomirClient;

    public List<StockPartFromTechnomir> getStockParts() {
        return tehnomirClient.getListStockParts()
                .orElseThrow(ObjectIsEmptyException::new);
    }

    public List<PartFromTechnomir> getPartsByCode(String code) {
        return tehnomirClient.getPartsByCode(code)
                .orElseThrow(ObjectIsEmptyException::new);
    }
}
