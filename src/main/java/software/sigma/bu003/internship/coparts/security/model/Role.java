package software.sigma.bu003.internship.coparts.security.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    SCOPE_MANAGER,
    SCOPE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
