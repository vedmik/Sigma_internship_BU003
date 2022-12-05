package software.sigma.bu003.internship.coparts.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.bu003.internship.coparts.security.model.Role;
import software.sigma.bu003.internship.coparts.security.model.User;
import software.sigma.bu003.internship.coparts.security.repository.UserRepository;
import software.sigma.bu003.internship.coparts.security.service.exception.UserNotFoundException;

import java.util.List;
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

    public void addNewRoleToUser(String email, Role role) {
       User user = getUserByEmail(email);

       user.getRoles().add(role);

       userRepository.save(user);
    }


    public void deleteRole(String email, Role role) {
        User user = getUserByEmail(email);

        user.getRoles().remove(role);

        userRepository.save(user);
    }

    public Set<Role> getAllUserRoles(String email) {
        return getUserByEmail(email).getRoles();
    }
}
