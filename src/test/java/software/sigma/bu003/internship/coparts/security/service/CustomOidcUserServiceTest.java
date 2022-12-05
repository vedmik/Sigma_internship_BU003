package software.sigma.bu003.internship.coparts.security.service;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import software.sigma.bu003.internship.coparts.security.model.User;
import software.sigma.bu003.internship.coparts.security.repository.UserRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomOidcUserServiceTest {

    private static final String EMAIL = "test@test.com";
    private static final String FULL_NAME = "Full Name";
    private static final String FIRST_NAME = "Full";
    private static final String LAST_NAME = "Name";
    private static final String PICTURE_URL = "URL";
    private static final String LOCALE = "uk";
    private static final String GOOGLE_ID = "subject1";
    private static final String DIFFERENT = "Some";
    private ClientRegistration.Builder clientRegistrationBuilder;

    private OAuth2AccessToken accessToken;

    private OidcIdToken idToken;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomOidcUserService sut;

    private MockWebServer server;

    @BeforeEach
    public void setup() throws Exception {
        this.server = new MockWebServer();
        this.server.start();

        this.clientRegistrationBuilder =
                ClientRegistration
                .withRegistrationId("registration-id")
                .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("read:user")
                .authorizationUri("https://example.com/login/oauth/authorize")
                .tokenUri("https://example.com/login/oauth/access_token")
                .jwkSetUri("https://example.com/oauth2/jwk")
                .issuerUri("https://example.com")
                .userInfoUri("https://api.example.com/user")
                .userNameAttributeName("id")
                .clientName("Client Name")
                .clientId("client-id")
                .clientSecret("client-secret")
                .userInfoUri(null)
                .userInfoAuthenticationMethod(AuthenticationMethod.HEADER)
                .userNameAttributeName(StandardClaimNames.SUB);

        this.accessToken =
                new OAuth2AccessToken(
                        OAuth2AccessToken.TokenType.BEARER,
                        "scopes",
                        Instant.now(),
                        Instant.now().plus(Duration.ofDays(1)),
                        new HashSet<>(Arrays.asList(OidcScopes.OPENID, OidcScopes.PROFILE))
                );

        Map<String, Object> idTokenClaims = new HashMap<>();
        idTokenClaims.put(IdTokenClaimNames.ISS, "https://provider.com");
        idTokenClaims.put(IdTokenClaimNames.SUB, GOOGLE_ID);
        idTokenClaims.put("email", EMAIL);
        idTokenClaims.put("name", FULL_NAME);
        idTokenClaims.put("given_name", FIRST_NAME);
        idTokenClaims.put("family_name", LAST_NAME);
        idTokenClaims.put("picture", PICTURE_URL);
        idTokenClaims.put("locale", LOCALE);

        this.idToken = new OidcIdToken("access-token", Instant.MIN, Instant.MAX, idTokenClaims);

        this.sut.setOauth2UserService(new DefaultOAuth2UserService());
    }

    @AfterEach
    public void cleanup() throws Exception {
        this.server.shutdown();
    }

    @Test
    void shouldCreateNewUserInDB() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(any());

        sut.loadUser(
                new OidcUserRequest(
                        this.clientRegistrationBuilder.build(),
                        this.accessToken,
                        this.idToken
                )
        );

        verify(userRepository).findByEmail(EMAIL);
        verify(userRepository).save(any());
    }

    @Test
    void shouldUpdateUserWhereFieldsIsNull() {
        User user = new User();
        user.setEmail(EMAIL);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(any());

        sut.loadUser(
                new OidcUserRequest(
                        this.clientRegistrationBuilder.build(),
                        this.accessToken,
                        this.idToken
                )
        );

        verify(userRepository).findByEmail(EMAIL);
        verify(userRepository).save(user);
    }

    @Test
    void shouldUpdateUserWhereFieldsIsNotNull() {
        User user = User.builder()
                .googleId(GOOGLE_ID)
                .fullName(FULL_NAME)
                .givenName(FIRST_NAME)
                .familyName(LAST_NAME)
                .email(EMAIL)
                .locale(LOCALE)
                .imageUrl(PICTURE_URL)
                .build();

        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(any());

        sut.loadUser(
                new OidcUserRequest(
                        this.clientRegistrationBuilder.build(),
                        this.accessToken,
                        this.idToken
                )
        );

        verify(userRepository).findByEmail(EMAIL);
        verify(userRepository).save(user);
    }

    @Test
    void shouldUpdateUserWhereFieldsIsNotNullAndDifferentValues() {
        User user = User.builder()
                .googleId(GOOGLE_ID + DIFFERENT)
                .fullName(FULL_NAME + DIFFERENT)
                .givenName(FIRST_NAME + DIFFERENT)
                .familyName(LAST_NAME + DIFFERENT)
                .email(EMAIL)
                .locale(LOCALE + DIFFERENT)
                .imageUrl(PICTURE_URL + DIFFERENT)
                .build();

        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(any());

        sut.loadUser(
                new OidcUserRequest(
                        this.clientRegistrationBuilder.build(),
                        this.accessToken,
                        this.idToken
                )
        );

        verify(userRepository).findByEmail(EMAIL);
        verify(userRepository).save(user);
    }
}
