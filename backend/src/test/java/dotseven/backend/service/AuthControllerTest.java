package dotseven.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dotseven.backend.controller.AuthController;
import dotseven.backend.dto.AuthRequest;
import dotseven.backend.dto.ErrorResponse;
import dotseven.backend.dto.LoginResponse;
import dotseven.backend.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private CaptchaService captchaService;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private AuthController authController;

    private MockMvc mvc;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {

        // Arrange
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mvc = MockMvcBuilders.standaloneSetup(authController)
                .build();
    }

    @Test
    void login_failedCaptcha() throws Exception {

        // Arrange
        AuthRequest request = new AuthRequest("testUser", "testPassword", "testToken", List.of("tstAns2", "testAns2"));
        when(captchaService.checkCaptcha(Mockito.anyString(), Mockito.any(List.class))).thenReturn(Optional.of(Boolean.FALSE));
        ErrorResponse expectedResponse = new ErrorResponse("Captcha verification failed");

        // Act
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
        assertEquals(response.getContentAsString(), mapper.writeValueAsString(expectedResponse));
    }

    @Test
    void login_appOk() throws Exception {

        // Arrange
        AuthRequest request = new AuthRequest("testUser", "testPassword", "testToken", List.of("tstAns2", "testAns2"));
        when(captchaService.checkCaptcha(Mockito.anyString(), Mockito.any(List.class))).thenReturn(Optional.of(Boolean.TRUE));
        when(authManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(tokenProvider.generateToken(null)).thenReturn("testJwtToken");

        // Act
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/authenticate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(response.getContentAsString(), mapper.writeValueAsString(new LoginResponse("testJwtToken")));
    }

}