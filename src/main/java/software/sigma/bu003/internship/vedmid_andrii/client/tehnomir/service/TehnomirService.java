package software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.client.TehnomirClient;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.Part;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.entity.responce.StockPart;
import software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.exception.ObjectIsEmptyException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TehnomirService {

    private final TehnomirClient tehnomirClient;

    public List<StockPart> getStockParts() {
        return tehnomirClient.getListStockParts()
                .orElseThrow(ObjectIsEmptyException::new);
    }

    public List<Part> getPartsByCode(String code) {
        return tehnomirClient.getPartsByCode(code)
                .orElseThrow(ObjectIsEmptyException::new);
    }
}
