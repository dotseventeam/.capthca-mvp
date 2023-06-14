package com.dotseven.captchaservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dotseven.captchaservice.captchatest.CaptchaTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest(classes = CaptchaTest.class)
public class CaptchaTestTests {
    
    CaptchaTest captchaTest = new CaptchaTest();
    
    
    @Test
    public void componentsAreNotNull() {
        assertNotNull(captchaTest);
    }
    @Test
    public void testGetCorrectAnswersNumbers(){
        assertEquals(captchaTest.getCorrectAnswers().size(),2);
    }
    @Test
    public void testGetCorrectAnswersDifferent(){
        List<String> correctAnswers = captchaTest.getCorrectAnswers();
        assertFalse( correctAnswers.get(0).equals(correctAnswers.get(1)));
    }

    @Test
    public void testImageNotNull(){
        assertNotNull(captchaTest.getBase64Image());
    }
}
