package software.sigma.bu003.internship.coparts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import software.sigma.bu003.internship.coparts.config.security.JwtService;
import software.sigma.bu003.internship.coparts.user.model.AuthenticationRequest;
import software.sigma.bu003.internship.coparts.user.model.RegisterRequest;
import software.sigma.bu003.internship.coparts.user.model.User;
import software.sigma.bu003.internship.coparts.user.model.UserRole;
import software.sigma.bu003.internship.coparts.user.service.UserService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({UserController.class})
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    private final String URL_TEMPLATE = "/users";

    private final String EMAIL = "test@Test.com";

    private final String PASSWORD = "pass";

    private final User testUser = User.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .build();

    private final String testUserJSON = """
            {
                     "email": "test@Test.com",
                     "password": "pass"
            }
            """;

    private final User testUserWithRole = User.builder()
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

    @Test
    void shouldReturnListIfSuccessfully() throws Exception {
        List<User> expectedList = List.of(testUser);

        when(userService.getAllUsers()).thenReturn(expectedList);

        mockMvc.perform(get(URL_TEMPLATE ))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("[ %s ]", testUserJSON)));
        verify(userService).getAllUsers();
    }

    @Test
    void shouldReturnUserIfSuccessfully() throws Exception {
        when(userService.getUser(EMAIL)).thenReturn(testUser);

        mockMvc.perform(get(String.format("%s/{email}",URL_TEMPLATE),EMAIL))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(testUserJSON));

        verify(userService).getUser(EMAIL);
    }

    @Test
    void shouldUserUpdateIfSuccessfully() throws Exception {
        when(userService.userUpdate(testUser)).thenReturn(testUser);

        mockMvc.perform(put(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserJSON))
                .andExpect(content().json(testUserJSON));

        verify(userService).userUpdate(testUser);
    }

    @Test
    void shouldUserDeleteIfSuccessfully() throws Exception {
        doNothing().when(userService).deleteUser(EMAIL);

        mockMvc.perform(delete(String.format("%s/{email}",URL_TEMPLATE),EMAIL))
                .andExpect(status().isOk());

        verify(userService).deleteUser(EMAIL);
    }

    @Test
    void shouldReturnUserIfUserRegister() throws Exception {
        String testUserWithRoleJSON = """
                {
                         "email": "test@Test.com",
                         "password": "pass",
                         "userRole": "USER"
                }
                """;

        when(userService.userRegister(testRegisterRequest)).thenReturn(testUserWithRole);

        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserJSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(testUserWithRoleJSON));

        verify(userService).userRegister(testRegisterRequest);
    }

    @Test
    void shouldReturnTokenIfUserAuth() throws Exception {
        String JWT_TOKEN = "token";
        when(userService.userAuth(testAuthenticationRequest)).thenReturn(JWT_TOKEN);

        mockMvc.perform(post(URL_TEMPLATE + "/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserJSON))
                .andExpect(status().isOk())
                .andExpect(content().string(JWT_TOKEN));

        verify(userService).userAuth(testAuthenticationRequest);
    }
}
