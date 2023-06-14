package com.dotseven.captchaservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import com.dotseven.captchaservice.controller.ServiceController;
import com.dotseven.captchaservice.data.Captcha;
import com.dotseven.captchaservice.services.AuthService;
import com.dotseven.captchaservice.services.CaptchaService;
import com.dotseven.captchaservice.token.Token;


public class ServiceControllerTests {
    CaptchaService captchaService = mock(CaptchaService.class);
    AuthService authService = mock(AuthService.class);
    Token token = mock(Token.class);
    ServiceController serviceController = new ServiceController(captchaService, authService);

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void checkAnswersTest() {
        List<String> answers = new ArrayList<String>(List.of("mela", "pera"));
        Token tokenfortest = new Token("123", "123", answers, LocalDateTime.now().plusMinutes(4));

        when(authService.authenticate(anyString(), anyString())).thenReturn(true);
        when(captchaService.checkAnswers(any(), anyList())).thenReturn(true);
        when(token.getExpiryTime()).thenReturn(LocalDateTime.now().plusMinutes(2));

        try (MockedStatic<Token> mockedStatic = mockStatic(Token.class)) {
            mockedStatic.when(() -> Token.decrypt("ZmdzZGdzZGZnc2RmZ3NkZmdzZGZnc2Rm")).thenReturn(tokenfortest);

            assertTrue(serviceController.checkAnswers(
                    "32", "213", "ZmdzZGdzZGZnc2RmZ3NkZmdzZGZnc2Rm", new ArrayList<>(List.of("mojito", "caramello"))));
        }

    }

    @Test
    public void checkAnswersExpiryTimeFaliureTest() {
        List<String> answers = new ArrayList<String>(List.of("mela", "pera"));
        Token tokenfortest = new Token("123", "123", answers, LocalDateTime.now().minusMinutes(4));

        when(authService.authenticate(anyString(), anyString())).thenReturn(true);
       
        when(captchaService.checkAnswers(any(), anyList())).thenReturn(true);
        when(token.getExpiryTime()).thenReturn(LocalDateTime.now().plusMinutes(2));

        try (MockedStatic<Token> mockedStatic = mockStatic(Token.class)) {
            mockedStatic.when(() -> Token.decrypt("ZmdzZGdzZGZnc2RmZ3NkZmdzZGZnc2Rm")).thenReturn(tokenfortest);

            assertFalse(serviceController.checkAnswers(
                    "32", "213", "ZmdzZGdzZGZnc2RmZ3NkZmdzZGZnc2Rm", new ArrayList<>(List.of("mojito", "caramello"))));
        }


    }


    @Test
    public void requestCaptchaTests(){
        Captcha captchaForTest = new Captcha("vfsdfa", "xcvzxvzfvdf",
                    List.of("cioccolato", "ananas", "mojito", "caramello"));
        
        when(authService.authenticate(anyString(), anyString())).thenReturn(true);
        when(captchaService.getRandomCaptcha(anyString())).thenReturn(captchaForTest);

        Captcha captchaResult = serviceController.requestCaptcha("123","123");
        assertNotNull(captchaResult);
        assertEquals(captchaForTest.getBase64Image(),captchaResult.getBase64Image());
        assertEquals(captchaForTest.getCriptoToken(),captchaResult.getCriptoToken());
    }
}
