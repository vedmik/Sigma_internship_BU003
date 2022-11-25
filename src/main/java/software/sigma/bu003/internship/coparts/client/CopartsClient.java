package software.sigma.bu003.internship.coparts.client;

import software.sigma.bu003.internship.coparts.entity.Part;

import java.util.List;

public interface CopartsClient {

    List<Part> getPartByCode(String code);

    List<Part> getAllParts();
}
