package software.sigma.bu003.internship.coparts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.bu003.internship.coparts.security.model.UserRole;
import software.sigma.bu003.internship.coparts.security.model.User;
import software.sigma.bu003.internship.coparts.security.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

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
    public List<UserRole> getAllRoles() {
        return List.of(UserRole.values());
    }

    @PostMapping("/{email}")
    public void addUserRoles(@PathVariable String email, @RequestBody List<UserRole> roles) {
        userService.addUserRoles(email, roles);
    }

    @DeleteMapping("/{email}/{role}")
    public void deleteRoleToUser(@PathVariable String email, @PathVariable UserRole role) {
        userService.deleteUserRole(email, role);
    }
}
