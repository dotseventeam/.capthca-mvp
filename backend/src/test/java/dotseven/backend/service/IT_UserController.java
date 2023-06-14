package dotseven.backend.service;


import dotseven.backend.controller.CaptchaController;
import dotseven.backend.controller.UserController;
import dotseven.backend.dto.CaptchaRequest;
import dotseven.backend.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IT_UserController {

    @LocalServerPort
    private int localPort;

    @Autowired
    private UserController userController;

    private TestRestTemplate api;

    private String url;

    @BeforeEach
    void setup() {

        // Arrange
        this.url = "http://localhost:" + this.localPort + "/api/users/me";
        this.api = new TestRestTemplate();
    }


    @Test
    void getUserInfo_fail_unauthenticated() {

        // Arrange

        // Act
        ResponseEntity response = api.getForEntity(
                this.url,
                String.class
        );

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


}


