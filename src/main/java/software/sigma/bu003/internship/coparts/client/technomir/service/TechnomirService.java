package software.sigma.bu003.internship.coparts.client.technomir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.coparts.client.CopartsClient;
import software.sigma.bu003.internship.coparts.client.technomir.client.TechnomirClient;
import software.sigma.bu003.internship.coparts.client.technomir.exception.TechnomirEmptyStockException;
import software.sigma.bu003.internship.coparts.client.technomir.exception.TechnomirPartNotFoundException;
import software.sigma.bu003.internship.coparts.client.technomir.mapper.TechnomirMapper;
import software.sigma.bu003.internship.coparts.entity.Part;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnomirService implements CopartsClient {

    private final TechnomirClient technomirClient;

    private final TechnomirMapper mapper;

    @Override
    public List<Part> getPartByCode(String code) {
        return technomirClient.getPartsByCode(code)
                .orElseThrow(() -> new TechnomirPartNotFoundException(code)).stream()
                .map(mapper::fromTehnomirPartToPart)
                .toList();
    }

    @Override
    public List<Part> getAllParts() {
        return technomirClient.getListStockParts()
                .orElseThrow(TechnomirEmptyStockException::new).stream()
                .map(mapper::fromStockTechnomirPartToPart)
                .toList();
    }
}
