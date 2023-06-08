package com.dotseven.captchaservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import com.dotseven.captchaservice.token.Token;
@SpringBootTest
public class TokenTests {
    LocalDateTime expirDateTime =LocalDateTime.now().plusMinutes(5);
    List<String> answers = List.of("libro","mela");
    Token token = new Token("123456789", "123456789", answers, expirDateTime);
    
    @Test
    public void testGetCaptchaId(){
        assertEquals(token.getCaptchaID(),"123456789");
    }

    @Test
    public void testGetAppId(){
        assertEquals(token.getAppID(),"123456789");
    }
    @Test
    public void testExpireTime(){
        assertEquals(token.getExpiryTime(),expirDateTime);
    }

    @Test
    public void testGetHashedAnswers(){
        assertEquals(token.getHashedCorrectAnswers(), DigestUtils.md5DigestAsHex(answers.toString().getBytes()).toUpperCase());
    }

    @Test
    public void testEncriptAndDecriptEquals(){
        String encrypted = token.encrypt();
            Token decryptedToken = Token.decrypt(encrypted);
            Assert.assertEquals(token.getAppID(), decryptedToken.getAppID());
            Assert.assertEquals(token.getCaptchaID(), decryptedToken.getCaptchaID());
            Assert.assertEquals(token.getHashedCorrectAnswers(), decryptedToken.getHashedCorrectAnswers());
            Assert.assertEquals(token.getExpiryTime(), decryptedToken.getExpiryTime());
    }
}
