package dotseven.backend.dto;

import java.io.Serializable;
import java.util.List;

public class CaptchaVerificationDto implements Serializable {

    private final String appid;
    private final String appsecret;
    private final String captchaToken;
    private final List<String> captchaAnswers;

    public CaptchaVerificationDto(String appid, String appsecret, String captchaToken, List<String> captchaAnswers) {
        this.appid = appid;
        this.appsecret = appsecret;
        this.captchaToken = captchaToken;
        this.captchaAnswers = captchaAnswers;
    }
}