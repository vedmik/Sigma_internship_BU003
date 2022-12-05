package software.sigma.bu003.internship.coparts.security.model;

import java.util.Map;

public class GoogleUserInfo {

    private final Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getGoogleId() {
        return (String) attributes.get("sub");
    }

    public String getFullName() {
        return (String) attributes.get("name");
    }

    public String getGivenName() {
        return (String) attributes.get("given_name");
    }

    public String getFamilyName() {
        return (String) attributes.get("family_name");
    }

    public String getPictureUrl() {
        return (String) attributes.get("picture");
    }

    public String getLocale(){
        return (String) attributes.get("locale");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }
}
