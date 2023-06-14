package com.dotseven.captchaservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.dotseven.captchaservice.captchatest.CaptchaTest;
import com.dotseven.captchaservice.captchatest.CaptchaTestRepository;
import com.dotseven.captchaservice.data.Captcha;
import com.dotseven.captchaservice.data.CaptchaFactory;
import com.dotseven.captchaservice.services.CaptchaService;
import com.dotseven.captchaservice.token.Token;
import com.dotseven.captchaservice.token.TokenFactory;
import com.dotseven.captchaservice.token.TokenRepository;

@SpringBootTest
public class CaptchaServiceTests {
    
    public CaptchaTestRepository repoCaptcha = mock(CaptchaTestRepository.class) ;
    public TokenRepository repoToken = mock(TokenRepository.class);
    public CaptchaTest captcha = mock(CaptchaTest.class);
    public TokenFactory tokenFactory = mock(TokenFactory.class);
    public CaptchaFactory captchaFactory = mock(CaptchaFactory.class);
    
    public CaptchaService captchaService ;
    public Token token = mock(Token.class);
    
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.captchaService = new CaptchaService(repoCaptcha, repoToken, tokenFactory, captchaFactory);
        
    }
   
   @Test
    public void getRandomCaptchaTest(){
        
        List<String> answers = new ArrayList<String>(List.of("mela","pera"));
        Token tokenfortest =new Token("123","123",answers,LocalDateTime.now().plusMinutes(4));
        when(captcha.getBase64Image()).thenReturn("534235323t");
        when(repoCaptcha.getRandomTest()).thenReturn(captcha);
        when(captcha.getCorrectAnswers()).thenReturn(answers);
        when(repoCaptcha.getRandomAnswers("mela","pera"))
                .thenReturn(List.of("cioccolato","ananas","mojito","caramello"));
        when(tokenFactory.buildToken(anyString(), anyString(), anyList(),any())).thenReturn(tokenfortest);
        when(captchaFactory.buildCaptcha(anyString(),anyString(), anyList()))
                .thenReturn(new Captcha("dfsdfsdfs", tokenfortest.encrypt(), answers));
        assertNotNull(captchaService.getRandomCaptcha("123"));
    }



    @Test
    public void checkAnswersTest() {
        List<String> answers = new ArrayList<String>(List.of("mela","pera"));
        Token tokenfortest =new Token("123","123",answers,LocalDateTime.now().plusMinutes(4));
        when(token.getAppID()).thenReturn("123");
        when(token.getCaptchaID()).thenReturn("123");
        when(repoToken.getToken(anyString(),anyString())).thenReturn(null);
        when(token.getHashedCorrectAnswers()).thenReturn("BF2E5D24781C7003FD7846D4C20465FE");
        try( MockedStatic<Token> mockedStatic = mockStatic(Token.class)){

            mockedStatic.when(()->Token.hashedAnswers(Mockito.anyList())).thenReturn("BF2E5D24781C7003FD7846D4C20465FE");
        }
        boolean verify = captchaService.checkAnswers(tokenfortest, answers);
        assertTrue(verify);
    }
    
    @Test
    public void checkAnswersFailTest() {
        List<String> answers = new ArrayList<String>(List.of("mela","pera"));
        Token tokenfortest =new Token("123","123",answers,LocalDateTime.now().plusMinutes(4));
        when(token.getAppID()).thenReturn("123");
        when(token.getCaptchaID()).thenReturn("123");
        when(repoToken.getToken(anyString(),anyString())).thenReturn(null);
        when(token.getHashedCorrectAnswers()).thenReturn("BF2E5D24781C7003FD7846D4C20465FE");
        try( MockedStatic<Token> mockedStatic = mockStatic(Token.class)){

            mockedStatic.when(()->Token.hashedAnswers(Mockito.anyList())).thenReturn("BF2E5D24781C7003FD7846D4C20465FE");
        }
        boolean verify = captchaService.checkAnswers(tokenfortest, new ArrayList<String>(List.of("mela","pepa")));
        assertFalse(verify);
    }

}
