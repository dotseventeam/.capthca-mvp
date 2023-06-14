package com.dotseven.captchaservice.data;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CaptchaFactory {
    public Captcha buildCaptcha(String base64Image, String criptoToken, List<String> answers) {
        return new Captcha(base64Image, criptoToken, answers);
    }
}
