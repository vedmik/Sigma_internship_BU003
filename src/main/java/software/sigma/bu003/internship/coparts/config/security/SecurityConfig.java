package software.sigma.bu003.internship.coparts.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    private static final String URL_USERS = "/users";
    private static final String URL_PARTS = "/parts";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_MANAGER = "MANAGER";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                    .disable()
                .authorizeHttpRequests()
                .antMatchers(URL_USERS + "/auth", "/swagger-ui/**", "/v3/api-docs/**","/","/openapi.yaml").permitAll()
                .antMatchers(HttpMethod.GET, URL_PARTS).permitAll()
                .antMatchers(HttpMethod.POST, URL_USERS).permitAll()
                .antMatchers(HttpMethod.GET, URL_USERS).hasAnyAuthority(ROLE_ADMIN, ROLE_MANAGER)
                .antMatchers(URL_PARTS).hasAnyAuthority(ROLE_ADMIN, ROLE_MANAGER)
                .antMatchers(URL_USERS + "/**", URL_USERS).hasAuthority(ROLE_ADMIN)
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
