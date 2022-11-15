package software.sigma.bu003.internship.coparts.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.coparts.entity.Part;
import software.sigma.bu003.internship.coparts.service.exception.PartNotFoundException;
import software.sigma.bu003.internship.coparts.repository.PartRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PartService {

    private final PartRepository partRepository;

    public Part createPart(Part part) {
        return partRepository.insert(part);
    }

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    public Part getPart(String brand, String code) {
        return findInDB(brand, code);
    }

    public Part updatePart(Part part) {
        findInDB(part.getBrand(), part.getCode());

        return partRepository.save(part);
    }

    public void deletePart(String brand, String code) {
        Part partFromDB = findInDB(brand, code);

        partRepository.delete(partFromDB);
    }

    private Part findInDB(String brand, String code){
        return partRepository.findById(brand + code)
                .orElseThrow(() -> new PartNotFoundException(brand, code));
    }
}
