package software.sigma.bu003.internship.coparts.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.coparts.config.security.JwtService;
import software.sigma.bu003.internship.coparts.user.model.AuthenticationRequest;
import software.sigma.bu003.internship.coparts.user.model.RegisterRequest;
import software.sigma.bu003.internship.coparts.user.model.User;
import software.sigma.bu003.internship.coparts.user.model.UserRole;
import software.sigma.bu003.internship.coparts.user.repository.UserRepository;
import software.sigma.bu003.internship.coparts.user.service.exception.UserAlreadyExistsException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public User userRegister(RegisterRequest request) {

        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException(
                            String.format("User with email: %s has already created", request.getEmail())
                    );
                });

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(UserRole.USER)
                .build();

        return userRepository.save(user);
    }

    public String userAuth(AuthenticationRequest request) {
        User user = (User) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ))
                .getPrincipal();

        return jwtService.generateToken(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User getUser(String email) {
        return checkUser(email);
    }

    public void deleteUser(String email) {
        User user = checkUser(email);
        userRepository.delete(user);
    }

    public User userUpdate(User userRequest) {
        User userFromDB = checkUser(userRequest.getEmail());

        userRequest.setId(userFromDB.getId());
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return userRepository.save(userRequest);
    }

    private User checkUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("This user has not found"));
    }
}
