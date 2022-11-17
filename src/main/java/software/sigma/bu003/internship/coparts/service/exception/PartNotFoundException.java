package software.sigma.bu003.internship.coparts.service.exception;

public class PartNotFoundException extends RuntimeException{
    public PartNotFoundException(String brand, String code) {
        super(String.format("Part with \"brand\": %s, \"code\": %s - is not found.", brand, code));
    }
}
