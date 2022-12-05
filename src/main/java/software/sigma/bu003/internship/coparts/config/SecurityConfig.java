package software.sigma.bu003.internship.coparts.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import software.sigma.bu003.internship.coparts.security.service.CustomOidcUserService;
import software.sigma.bu003.internship.coparts.security.service.UserService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    private final CustomOidcUserService customOidcUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeRequests()
                    .antMatchers("/users/roles","users/*/roles/**")
                        .hasAuthority("SCOPE_ADMIN")
                    .antMatchers("/users","/users/**")
                        .hasAnyAuthority("SCOPE_MANAGER", "SCOPE_ADMIN")
                    .anyRequest()
                        .authenticated()
                .and()
                    .oauth2Login()
                        .userInfoEndpoint().oidcUserService(customOidcUserService)
                    .and()
                .and()
                .build();
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return this::mapAuthorities;
    }

    private Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>(authorities);

        authorities.stream()
                .filter(OidcUserAuthority.class::isInstance)
                .map(OidcUserAuthority.class::cast)
                .map(oidcUserAuthority -> oidcUserAuthority
                        .getAttributes()
                        .get("email")
                        .toString())
                .map(userService::getAllUserRoles)
                .forEach(mappedAuthorities::addAll);

        return mappedAuthorities;
    }
}
