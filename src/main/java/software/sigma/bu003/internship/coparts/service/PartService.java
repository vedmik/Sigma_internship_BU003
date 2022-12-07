package software.sigma.bu003.internship.coparts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.coparts.client.CopartsClient;
import software.sigma.bu003.internship.coparts.entity.Part;
import software.sigma.bu003.internship.coparts.service.exception.PartNotFoundException;
import software.sigma.bu003.internship.coparts.repository.PartRepository;
import software.sigma.bu003.internship.coparts.service.exception.PartAlreadyCreatedException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;

    private final CopartsClient copartsClient;

    public Part createPart(Part part) {
        try {
            return partRepository.insert(part);
        } catch (RuntimeException ex) {
            throw new PartAlreadyCreatedException(part.getBrand(), part.getCode());
        }
    }

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    public Part getPart(String brand, String code) {
        return checkIfPartExistsInDB(brand, code);
    }

    public Part updatePart(Part part) {
        checkIfPartExistsInDB(part.getBrand(),part.getCode());

        return partRepository.save(part);
    }

    public void deletePart(String brand, String code) {
        checkIfPartExistsInDB(brand, code);
        
        partRepository.deleteById(brand + code);
    }

    private Part checkIfPartExistsInDB(String brand, String code){
        return partRepository.findById(brand.replaceAll("\\s","") + code)
                .orElseThrow(() -> new PartNotFoundException(brand, code));
    }

    public List<Part> synchronizeWithTechnomir() {

        return partRepository.saveAll(copartsClient.getAllParts());
    }
}
