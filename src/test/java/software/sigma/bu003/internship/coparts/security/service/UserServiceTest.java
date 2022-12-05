package software.sigma.bu003.internship.coparts.security.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.sigma.bu003.internship.coparts.security.model.Role;
import software.sigma.bu003.internship.coparts.security.model.User;
import software.sigma.bu003.internship.coparts.security.repository.UserRepository;
import software.sigma.bu003.internship.coparts.security.service.exception.UserNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService sut;

    private static final String EMAIL = "test@test.com";

    static final Set<Role> ALL_ROLES = new HashSet<>(List.of(Role.SCOPE_ADMIN, Role.SCOPE_MANAGER));
    private static final User testUser = User.builder()
            .roles(ALL_ROLES)
            .build();

    @Test
    void shouldReturnAllUsersIfSuccessfully() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        sut.getAllUsers();

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnUserByEmailIfSuccessfully() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(testUser));

        sut.getUserByEmail(EMAIL);

        verify(userRepository, times(1)).findByEmail(EMAIL);
    }

    @Test
    void shouldThrowExceptionIfUserNotFound() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> sut.getUserByEmail(EMAIL));

        verify(userRepository, times(1)).findByEmail(EMAIL);
    }

    @Test
    void shouldAddNewRoleToUserIfSuccessfully() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(any());

        sut.addNewRoleToUser(EMAIL, Role.SCOPE_MANAGER);

        verify(userRepository, times(1)).findByEmail(EMAIL);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void shouldDeleteRoleIfSuccessfully() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(any());

        sut.deleteRole(EMAIL, Role.SCOPE_MANAGER);

        verify(userRepository, times(1)).findByEmail(EMAIL);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void shouldReturnAllUserRoles() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(testUser));

        sut.getAllUserRoles(EMAIL);

        verify(userRepository, times(1)).findByEmail(EMAIL);
    }
}
