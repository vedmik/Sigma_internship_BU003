package software.sigma.bu003.internship.coparts.security.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    SCOPE_MANAGER,
    SCOPE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
