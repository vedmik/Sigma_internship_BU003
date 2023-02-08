package software.sigma.bu003.internship.coparts.part.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.coparts.client.CopartsClient;
import software.sigma.bu003.internship.coparts.part.entity.PageParts;
import software.sigma.bu003.internship.coparts.part.entity.Part;
import software.sigma.bu003.internship.coparts.part.service.exception.PartNotFoundException;
import software.sigma.bu003.internship.coparts.part.repository.PartRepository;
import software.sigma.bu003.internship.coparts.part.service.exception.PartAlreadyCreatedException;

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

    public PageParts getAllParts(int page, int size) {
        Pageable paging = PageRequest.of(page, size);

        Page<Part> pageParts = partRepository.findAll(paging);

        return new PageParts(pageParts);
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

    public List<Part> syncWithTechnomir() {

        return partRepository.saveAll(copartsClient.getAllParts());
    }
}
