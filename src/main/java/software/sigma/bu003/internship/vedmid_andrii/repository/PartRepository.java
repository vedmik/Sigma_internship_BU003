package software.sigma.bu003.internship.vedmid_andrii.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import software.sigma.bu003.internship.vedmid_andrii.entity.Part;

@Repository
public interface PartRepository extends MongoRepository<Part, String> {

    Part findByBrandAndCode(String brand, String code);
}
