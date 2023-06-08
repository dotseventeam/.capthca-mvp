package com.dotseven.captchaservice.data;

import java.util.List;

public class Captcha {
    private String base64Image;
    private String criptoToken;
    private List<String> captchaOptions;

    public Captcha(String base64Image, String criptoToken, List<String> answers) {
        this.base64Image = base64Image;
        this.criptoToken = criptoToken;
        this.captchaOptions = answers;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public String getCriptoToken() {
        return criptoToken;
    }

    public List<String> getCaptchaOptions() {
        return captchaOptions;
    }

}
