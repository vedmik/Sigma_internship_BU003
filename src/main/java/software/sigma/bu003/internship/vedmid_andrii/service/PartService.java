package software.sigma.bu003.internship.vedmid_andrii.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.vedmid_andrii.entity.Part;
import software.sigma.bu003.internship.vedmid_andrii.exception.PartNotFoundException;
import software.sigma.bu003.internship.vedmid_andrii.repository.PartRepository;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PartService {

    private final PartRepository partRepository;

    public Part createPart(Part part) {
        checkIsPart(part.getBrand(), part.getCode());

        Part partFromDB = partRepository.findByBrandAndCode(part.getBrand(), part.getCode());

        if(!Objects.isNull(partFromDB)){
            return partFromDB;
        } else {
            return partRepository.save(part);
        }
    }

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    public Part getPart(String brand, String code) {
        Part partFromDB = partRepository.findByBrandAndCode(brand, code);

        checkPartInDB(partFromDB);

        return partFromDB;
    }

    public Part updatePart(Part part) {
        checkIsPart(part.getBrand(), part.getCode());

        Part partFromDB = partRepository.findByBrandAndCode(part.getBrand(), part.getCode());

        checkPartInDB(partFromDB);

        if(!Objects.isNull(part.getPrice())) {
            partFromDB.setPrice(part.getPrice());
        }
        if(!Objects.isNull(part.getDescription())) {
            partFromDB.setDescription(part.getDescription());
        }

        return partRepository.save(partFromDB);
    }

    public void deletePart(String brand, String code) {
        Part partFromDB = partRepository.findByBrandAndCode(brand, code);

        checkPartInDB(partFromDB);

        partRepository.delete(partFromDB);
    }

    private void checkIsPart(String brand, String code) {
        if(Objects.isNull(brand) || Objects.isNull(code)) {
            throw new PartNotFoundException("Missing brand or part code");
        }
    }
    private void checkPartInDB(Part partFromDB) {
        if(Objects.isNull(partFromDB)) throw new PartNotFoundException("This part is not available in the DB");
    }
}
