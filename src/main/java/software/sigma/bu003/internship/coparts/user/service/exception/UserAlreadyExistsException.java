package software.sigma.bu003.internship.coparts.user.service.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String email) {
        super(String.format("User with email: %s has already created", email));
    }
}
