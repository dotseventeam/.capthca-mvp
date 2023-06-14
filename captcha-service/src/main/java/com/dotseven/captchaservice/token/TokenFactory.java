package com.dotseven.captchaservice.token;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

@Component 
public class TokenFactory { 
    public Token buildToken(String captchaID, String appID, List<String> CorrectAnswers, LocalDateTime expiryTime){
        return new Token(captchaID, appID, CorrectAnswers, expiryTime);
    }
}
