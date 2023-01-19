package software.sigma.bu003.internship.coparts.user.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER,
    MANAGER,
    ADMIN;

    @Override
    public String getAuthority() {
        return toString();
    }
}
