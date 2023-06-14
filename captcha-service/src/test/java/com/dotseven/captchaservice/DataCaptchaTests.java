package com.dotseven.captchaservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dotseven.captchaservice.data.Captcha;

@SpringBootTest
public class DataCaptchaTests {

    @Test
    public void testGetBase64Image() {
        String base64Image = "abc123"; // Esempio di immagine codificata in base64
        String criptoToken = "token123";
        List<String> captchaOptions = Arrays.asList("Option 1", "Option 2", "Option 3");

        Captcha captcha = new Captcha(base64Image, criptoToken, captchaOptions);

        String retrievedBase64Image = captcha.getBase64Image();
        assertEquals(base64Image, retrievedBase64Image);
    }

    @Test
    public void testGetCriptoToken() {
        String base64Image = "abc123";
        String criptoToken = "token123"; // Esempio di token criptato
        List<String> captchaOptions = Arrays.asList("Option 1", "Option 2", "Option 3");

        Captcha captcha = new Captcha(base64Image, criptoToken, captchaOptions);

        String retrievedCriptoToken = captcha.getCriptoToken();
        assertEquals(criptoToken, retrievedCriptoToken);
    }

    @Test
    public void testGetCaptchaOptions() {
        String base64Image = "abc123";
        String criptoToken = "token123";
        List<String> captchaOptions = Arrays.asList("Option 1", "Option 2", "Option 3");

        Captcha captcha = new Captcha(base64Image, criptoToken, captchaOptions);

        List<String> retrievedCaptchaOptions = captcha.getCaptchaOptions();
        assertEquals(captchaOptions, retrievedCaptchaOptions);
    }
}
