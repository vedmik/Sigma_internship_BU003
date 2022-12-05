package software.sigma.bu003.internship.coparts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import software.sigma.bu003.internship.coparts.security.model.Role;
import software.sigma.bu003.internship.coparts.security.model.User;
import software.sigma.bu003.internship.coparts.security.service.UserService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({UserAndRoleController.class})
@AutoConfigureMockMvc(addFilters = false)
class UserAndRoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final String URL_TEMPLATE = "/users";
    private final String URL_TEMPLATE_WITH_USER_EMAIL_AND_ROLE = "/users/{email}/roles/{role}";
    private final String USER_EMAIL = "test@test.com";
    private final String ROLE_ADMIN = "SCOPE_ADMIN";

    private final User testUser = User.builder()
            .email(USER_EMAIL)
            .build();

    private final String testUserJSON = """
            {
                "email": "test@test.com"
            }
            """;

    @Test
    void shouldReturnListUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn((List.of(testUser)));

        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("[ %s ]", testUserJSON)));

        verify(userService).getAllUsers();
    }

    @Test
    void shouldReturnUserByEmail() throws Exception {
        when(userService.getUserByEmail(USER_EMAIL)).thenReturn(testUser);

        mockMvc.perform(get(URL_TEMPLATE + "/{email}", USER_EMAIL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(testUserJSON));

        verify(userService).getUserByEmail(USER_EMAIL);
    }

    @Test
    void shouldReturnAllRoles() throws Exception {
        String testRolesJSON = "[ \"SCOPE_MANAGER\", \"SCOPE_ADMIN\"]";

        mockMvc.perform(get(URL_TEMPLATE + "/roles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(testRolesJSON));
    }

    @Test
    void shouldAddRoleToUser() throws Exception {
        doNothing().when(userService).addNewRoleToUser(USER_EMAIL, Role.SCOPE_ADMIN);

        mockMvc.perform(get(URL_TEMPLATE_WITH_USER_EMAIL_AND_ROLE, USER_EMAIL, ROLE_ADMIN))
                .andExpect(status().isOk());

        verify(userService).addNewRoleToUser(USER_EMAIL, Role.SCOPE_ADMIN);
    }

    @Test
    void shouldDeleteRoleToUser() throws Exception {
        doNothing().when(userService).deleteRole(USER_EMAIL, Role.SCOPE_ADMIN);

        mockMvc.perform(delete(URL_TEMPLATE_WITH_USER_EMAIL_AND_ROLE, USER_EMAIL, ROLE_ADMIN))
                .andExpect(status().isOk());

        verify(userService).deleteRole(USER_EMAIL, Role.SCOPE_ADMIN);
    }
}
