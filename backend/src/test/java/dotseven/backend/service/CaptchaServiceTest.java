package dotseven.backend.service;

import dotseven.backend.dto.CaptchaRequest;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class CaptchaServiceTest {

    private RestTemplate restTemplate;

    private CaptchaService captchaService;


    @BeforeEach
    void setUp() {

        // Arrange
        this.restTemplate = Mockito.mock(RestTemplate.class);
        this.captchaService = new CaptchaService(this.restTemplate);
    }

    @Test
    void getNewCaptcha_fail_WithException() {

        // Arrange
        Mockito.when(restTemplate.postForEntity(
                        Mockito.anyString(),
                        Mockito.any(HttpEntity.class),
                        Mockito.eq(CaptchaRequest.class)))
                .thenThrow(new RuntimeException(
                        "Some exception occurred calling \"restTemplate.postForEntity\""
                ));

        // Act
        Optional<CaptchaRequest> result = captchaService.getNewCaptcha();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getNewCaptcha_fail_InternalServerError() {

        // Arrange
        Mockito.when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(CaptchaRequest.class))
        ).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        // Act
        Optional<CaptchaRequest> result = captchaService.getNewCaptcha();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getNewCaptcha_fail_Unauthorized() {

        // Arrange
        Mockito.when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(CaptchaRequest.class))
        ).thenReturn(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));

        // Act
        Optional<CaptchaRequest> result = captchaService.getNewCaptcha();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getNewCaptcha_success() {

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

        // Act
        Optional<CaptchaRequest> result = captchaService.getNewCaptcha();

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockCaptcha, result.get());
    }


    @Test
    void checkCaptcha_fail_WithException() {

        // Arrange
        Mockito.when(restTemplate.postForEntity(
                        Mockito.anyString(),
                        Mockito.any(HttpEntity.class),
                        Mockito.eq(Boolean.class)))
                .thenThrow(new RuntimeException(
                        "Some exception occurred calling \"restTemplate.postForEntity\""
                ));

        // Act
        Optional<Boolean> result = captchaService.checkCaptcha(
                "testToken",
                Collections.nCopies(2, "testAns"));

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void checkCaptcha_fail_InternalServerError() {

        // Arrange
        Mockito.when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(Boolean.class))
        ).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        // Act
        Optional<Boolean> result = captchaService.checkCaptcha(
                "testToken",
                Collections.nCopies(2, "testAns"));

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void checkCaptcha_fail_Unauthorized() {

        // Arrange
        Mockito.when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(Boolean.class))
        ).thenReturn(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));

        // Act
        Optional<Boolean> result = captchaService.checkCaptcha(
                "testToken",
                Collections.nCopies(2, "testAns"));

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void checkCaptcha_success() {

        // Arrange
        Boolean mockResult = Boolean.TRUE;
        Mockito.when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(Boolean.class))
        ).thenReturn(ResponseEntity
                .ok()
                .body(mockResult)
        );

        // Act
        Optional<Boolean> result = captchaService.checkCaptcha(
                "testToken",
                Collections.nCopies(2, "testAns"));

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockResult, result.get());
    }
}
