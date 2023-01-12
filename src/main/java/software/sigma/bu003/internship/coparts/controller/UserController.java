package software.sigma.bu003.internship.coparts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.bu003.internship.coparts.user.model.AuthenticationRequest;
import software.sigma.bu003.internship.coparts.user.model.RegisterRequest;
import software.sigma.bu003.internship.coparts.user.model.User;
import software.sigma.bu003.internship.coparts.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable String email) {
        return userService.getUser(email);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User userUpdate(@RequestBody User user) {
        return userService.userUpdate(user);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public void userDelete(@PathVariable String email) {
        userService.deleteUser(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User userRegister(@RequestBody RegisterRequest request) {
        return userService.userRegister(request);
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public String userAuth(@RequestBody AuthenticationRequest request) {
        return userService.userAuth(request);
    }
}
