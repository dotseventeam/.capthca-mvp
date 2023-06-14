package dotseven.backend.service;


import dotseven.backend.controller.CaptchaController;
import dotseven.backend.dto.CaptchaRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IT_CaptchaController {

    @LocalServerPort
    private int localPort;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private CaptchaController captchaController;

    private TestRestTemplate api;
    private JacksonTester jackson;

    private String url;

    @BeforeEach
    void setup() {

        // Arrange
        this.url = "http://localhost:"+this.localPort+"/api/captcha/requestCaptcha";
        this.api = new TestRestTemplate();
    }


    @Test
    void requestCaptcha_fail_CaptchaApiAuthError() {

        // Arrange
        Mockito.when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(CaptchaRequest.class))
        ).thenReturn(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));

        // Act
        ResponseEntity response = api.getForEntity(
                this.url,
                String.class
        );

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void requestCaptcha_fail_CaptchaApiInternalError() {

        // Arrange
        Mockito.when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(CaptchaRequest.class))
        ).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        // Act
        ResponseEntity response = api.getForEntity(
                this.url,
                String.class
        );

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void requestCaptcha_success_NewCaptchaReceived() {

        // Arrange
        CaptchaRequest mockCaptcha = new CaptchaRequest(
                "testToken",
                "testImage",
                Collections.nCopies(8, "testAns")
        );
        Mockito.when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(CaptchaRequest.class))
        ).thenReturn(ResponseEntity
                .ok()
                .body(mockCaptcha)
        );
        String expectedResponse = "{\"criptoToken\":\"testToken\",\"base64Image\":\"testImage\",\"captchaOptions\":[\"testAns\",\"testAns\",\"testAns\",\"testAns\",\"testAns\",\"testAns\",\"testAns\",\"testAns\"]}";
        // Act
        ResponseEntity response = api.getForEntity(
                this.url,
                String.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

}
