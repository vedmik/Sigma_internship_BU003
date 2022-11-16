package software.sigma.bu003.internship.coparts.service.exception;

public class PartAlreadyCreatedException extends RuntimeException {
    public PartAlreadyCreatedException(String brand, String code) {
        super(String.format("Part with \"brand\": %s, \"code\": %s - has already been created!", brand, code));
    }
}
