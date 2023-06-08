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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CaptchaServiceTest {

    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    CaptchaService captchaService;

    @BeforeEach
    void setUp() {

        // Arrange
        captchaService = new CaptchaService(restTemplate);
    }

    @Test
    void checkCaptcha_unreachable() {

        // Arrange
        ResponseEntity<Boolean> mockResponse = new ResponseEntity<>(Boolean.FALSE, HttpStatus.valueOf(500));

        when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(Boolean.class))
        ).thenReturn(mockResponse);

        // Act
        try {
            captchaService.checkCaptcha("", List.of(""));

        // Assert
            fail("Exception should have been thrown");
        } catch (RuntimeException ex) {
            assertEquals("Unreachable Service", ex.getMessage());
        }

    }

    @Test
    void checkCaptcha_unauthorized() {

        // Arrange
        ResponseEntity<Boolean> mockResponse = new ResponseEntity<>(Boolean.FALSE, HttpStatus.UNAUTHORIZED);

        when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(Boolean.class))
        ).thenReturn(mockResponse);

        // Act
        try {
            captchaService.checkCaptcha("", List.of(""));

            // Assert
            fail("Exception should have been thrown");
        } catch (RuntimeException ex) {
            assertEquals("Expired Credentials", ex.getMessage());
        }

    }

    @Test
    void checkCaptcha_goodRequest() {

        // Arrange
        ResponseEntity<Boolean> mockResponse = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);

        when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(Boolean.class))
        ).thenReturn(mockResponse);

        // Act
        try {
            boolean result = captchaService.checkCaptcha("", List.of(""));

            // Assert
            assertTrue(result);
        } catch (Exception ex) {
            fail("Exception should not have been thrown");
        }

    }

    @Test
    void getNewCaptcha_unreachable() {

        // Arrange
        CaptchaRequest mockRequest = new CaptchaRequest();
        ResponseEntity<CaptchaRequest> mockResponse = new ResponseEntity<>(mockRequest, HttpStatus.valueOf(500));

        when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(CaptchaRequest.class))
        ).thenReturn(mockResponse);

        // Act
        try {
            captchaService.getNewCaptcha();

            // Assert
            fail("Exception should have been thrown");
        } catch (RuntimeException ex) {
            assertEquals("Unreachable Service", ex.getMessage());
        }

    }

    @Test
    void getNewCaptcha_unauthorized() {

        // Arrange
        CaptchaRequest mockRequest = new CaptchaRequest();
        ResponseEntity<CaptchaRequest> mockResponse = new ResponseEntity<>(mockRequest, HttpStatus.UNAUTHORIZED);

        when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(CaptchaRequest.class))
        ).thenReturn(mockResponse);

        // Act
        try {
            captchaService.getNewCaptcha();

            // Assert
            fail("Exception should have been thrown");
        } catch (RuntimeException ex) {
            assertEquals("Expired Credentials", ex.getMessage());
        }

    }

    @Test
    void getNewCaptcha_goodRequest() {

        // Arrange
        CaptchaRequest mockRequest = new CaptchaRequest();
        ResponseEntity<CaptchaRequest> mockResponse = new ResponseEntity<>(mockRequest, HttpStatus.OK);

        when(restTemplate.postForEntity(
                Mockito.anyString(),
                Mockito.any(HttpEntity.class),
                Mockito.eq(CaptchaRequest.class))
        ).thenReturn(mockResponse);

        // Act
        try {
            CaptchaRequest result = captchaService.getNewCaptcha();

            // Assert
            assertEquals(result, mockRequest);
        } catch (Exception ex) {
            fail("Exception should not have been thrown");
        }

    }
}