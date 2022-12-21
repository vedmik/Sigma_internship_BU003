package software.sigma.bu003.internship.coparts.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.coparts.security.model.UserRole;
import software.sigma.bu003.internship.coparts.security.model.User;
import software.sigma.bu003.internship.coparts.security.repository.UserRepository;
import software.sigma.bu003.internship.coparts.security.service.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public void addUserRoles(String email, List<UserRole> roles) {
       User user = getUserByEmail(email);

       user.getRoles().addAll(roles);

       userRepository.save(user);
    }


    public void deleteUserRole(String email, UserRole role) {
        User user = getUserByEmail(email);

        user.getRoles().remove(role);

        userRepository.save(user);
    }

    public Set<UserRole> getAllUserRoles(String email) {
        return getUserByEmail(email).getRoles();
    }

    public Optional<User> getOptionalUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
