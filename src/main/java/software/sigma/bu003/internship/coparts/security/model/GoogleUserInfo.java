package software.sigma.bu003.internship.coparts.security.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Data
public class GoogleUserInfo {

    private final String googleId;
    private final String firstName;
    private final String lastName;
    private final String pictureUrl;
    private final String locale;
    @Email
    @NotEmpty
    private final String email;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.googleId = (String) attributes.get("sub");
        this.firstName = (String) attributes.get("given_name");
        this.lastName = (String) attributes.get("family_name");
        this.pictureUrl = (String) attributes.get("picture");
        this.locale = (String) attributes.get("locale");
        this.email = (String) attributes.get("email");
    }
}
