package software.sigma.bu003.internship.coparts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.bu003.internship.coparts.security.model.Role;
import software.sigma.bu003.internship.coparts.security.model.User;
import software.sigma.bu003.internship.coparts.security.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserAndRoleController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public User getUser(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return List.of(Role.values());
    }

    @GetMapping("/{email}/roles/{role}")
    public void setRoleToUser(@PathVariable String email, @PathVariable Role role) {
        userService.addNewRoleToUser(email, role);
    }

    @DeleteMapping("/{email}/roles/{role}")
    public void deleteRoleToUser(@PathVariable String email, @PathVariable Role role) {
        userService.deleteRole(email, role);
    }
}
