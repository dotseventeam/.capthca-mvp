package dotseven.backend.service;


import dotseven.backend.controller.AuthController;
import dotseven.backend.controller.UserController;
import dotseven.backend.dto.AuthRequest;
import dotseven.backend.dto.CaptchaRequest;
import dotseven.backend.entity.User;
import dotseven.backend.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IT_AuthController {

    @LocalServerPort
    private int localPort;

    @Autowired
    private AuthController authController;

    @MockBean
    private RestTemplate restTemplate;

    private TestRestTemplate api;

    private String url;

    @Autowired
    private  UserService userService;
    @Autowired
    private  PasswordEncoder passwordEncoder;


    @BeforeEach
    void setup() {

        // Arrange
        this.url = "http://localhost:" + this.localPort + "/api/auth/authenticate";
        this.api = new TestRestTemplate();
    }


    @Test
    void authenticate_fail_wrongCaptchaAnswer() {

        // Arrange
        when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(Boolean.class))
        ).thenReturn(ResponseEntity
                .status(HttpStatus.OK)
                .body(Boolean.FALSE)
        );
        AuthRequest dto = new AuthRequest(
                "admin",
                "admin",
                "testCaptchaToken",
                Collections.nCopies(8, "testCaptchaAnswer")
        );


        // Act
        ResponseEntity response = api.postForEntity(
                this.url,
                new HttpEntity(dto),
                String.class
        );

        // Assert
        assertEquals(HttpStatus.valueOf(422), response.getStatusCode());
    }

    @Test
    void authenticate_fail_CaptchaServiceError() {

        // Arrange
        when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(Boolean.class))
        ).thenReturn(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));
        AuthRequest dto = new AuthRequest(
                "admin",
                "admin",
                "testCaptchaToken",
                Collections.nCopies(8, "testCaptchaAnswer")
        );


        // Act
        ResponseEntity response = api.postForEntity(
                this.url,
                new HttpEntity(dto),
                String.class
        );

        // Assert
        assertEquals(HttpStatus.valueOf(422), response.getStatusCode());
    }

    @Test
    void authenticate_fail_WrongCredentials() {

        // Arrange
        when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(Boolean.class))
        ).thenReturn(ResponseEntity
                .status(HttpStatus.OK)
                .body(Boolean.TRUE)
        );
        AuthRequest dto = new AuthRequest(
                "wrong",
                "wrong",
                "testCaptchaToken",
                Collections.nCopies(8, "testCaptchaAnswer")
        );


        // Act
        ResponseEntity response = api.postForEntity(
                this.url,
                new HttpEntity(dto),
                String.class
        );

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void authenticate_success() {


        User userToSave = new User(
                "test",
                "test",
                "Admin",
                "Lastname",
                LocalDate.EPOCH,
                "admin@mycompany.com",
                'X', null);
        userToSave.setPasswordHash(passwordEncoder.encode(userToSave.getPasswordHash()));
        userService.saveUser(userToSave);

        // Arrange
        when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(Boolean.class))
        ).thenReturn(ResponseEntity
                .status(HttpStatus.OK)
                .body(Boolean.TRUE)
        );
        AuthRequest dto = new AuthRequest(
                "test",
                "test",
                "testCaptchaToken",
                Collections.nCopies(8, "testCaptchaAnswer")
        );


        // Act
        ResponseEntity response = api.postForEntity(
                this.url,
                new HttpEntity(dto),
                String.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}


