package software.sigma.bu003.internship.coparts.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import software.sigma.bu003.internship.coparts.config.security.JwtService;
import software.sigma.bu003.internship.coparts.user.model.AuthenticationRequest;
import software.sigma.bu003.internship.coparts.user.model.RegisterRequest;
import software.sigma.bu003.internship.coparts.user.model.User;
import software.sigma.bu003.internship.coparts.user.model.UserRole;
import software.sigma.bu003.internship.coparts.user.repository.UserRepository;
import software.sigma.bu003.internship.coparts.user.service.exception.UserAlreadyExistsException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private UsernamePasswordAuthenticationToken authentication;

    @InjectMocks
    private UserService sut;

    private final String EMAIL = "test@Test.com";

    private final String PASSWORD = "pass";

    private final User testUser = User.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .userRole(UserRole.USER)
            .build();

    private final RegisterRequest testRegisterRequest = RegisterRequest.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .build();

    private final AuthenticationRequest testAuthenticationRequest = AuthenticationRequest.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .build();

    private final Authentication testAuth = new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD);

    @Test
    void shouldReturnUserIfSuccessfully() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(userRepository.save(testUser)).thenReturn(testUser);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);

        sut.userRegister(testRegisterRequest);

        verify(userRepository, times(1)).findByEmail(EMAIL);
        verify(userRepository, times(1)).save(testUser);
        verify(passwordEncoder,times(1)).encode(PASSWORD);
    }

    @Test
    void shouldThrowExceptionIfUserHasAlreadyRegistered() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(testUser));

        assertThrows(UserAlreadyExistsException.class, () -> sut.userRegister(testRegisterRequest));

        verify(userRepository, times(1)).findByEmail(EMAIL);
    }

    @Test
    void ShouldReturnJwtIfSuccessfully() {
        String JWT_TOKEN = "token";
        when(jwtService.generateToken(testUser)).thenReturn(JWT_TOKEN);
        when(authenticationManager.authenticate(testAuth)).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);

        sut.userAuth(testAuthenticationRequest);

        verify(authenticationManager, times(1)).authenticate(testAuth);
        verify(jwtService, times(1)).generateToken(testUser);
    }

    @Test
    void shouldReturnListUsersIfSuccessfully() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        sut.getAllUsers();

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldUserIfSuccessfully() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(testUser));

        sut.getUser(EMAIL);

        verify(userRepository, times(1)).findByEmail(EMAIL);
    }

    @Test
    void shouldThrowExceptionIfUserNotFound() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> sut.getUser(EMAIL));

        verify(userRepository, times(1)).findByEmail(EMAIL);
    }

    @Test
    void deleteUser() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(testUser));

        sut.deleteUser(EMAIL);

        verify(userRepository, times(1)).findByEmail(EMAIL);
    }

    @Test
    void userUpdate() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(testUser);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);

        sut.userUpdate(testUser);

        verify(userRepository, times(1)).findByEmail(EMAIL);
        verify(userRepository, times(1)).save(testUser);
        verify(passwordEncoder).encode(PASSWORD);
    }
}