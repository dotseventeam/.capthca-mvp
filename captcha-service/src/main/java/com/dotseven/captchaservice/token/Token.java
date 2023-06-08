package com.dotseven.captchaservice.token;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import javax.persistence.*;

import org.springframework.util.DigestUtils;

@Entity
@Table(name="token")
public class Token implements Serializable {
    public static final String SECRET_KEY = "YourSecretKeyYou"; // create getKey

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "captcha_id", nullable = false)
    private String captchaID;
    @Column(name = "app_id", nullable = false)
    private String appID;
    @Column(name = "hashed_correct_answers", nullable = false)
    private String hashedCorrectAnswers;
    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    public Token(){}
    public Token(String captchaID, String appID, String hashedCorrectAnswers, LocalDateTime expiryTime) {
        this.captchaID = captchaID;
        this.appID = appID;
        this.hashedCorrectAnswers = hashedCorrectAnswers;
        this.expiryTime = expiryTime;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public Token(String captchaID, String appID, List<String> CorrectAnswers, LocalDateTime expiryTime) {

        this.captchaID = captchaID;
        this.appID = appID;
        this.hashedCorrectAnswers = hashedAnswers(CorrectAnswers);
        this.expiryTime = expiryTime;
    }

    public static String getSecretKey() {
        return SECRET_KEY;
    }

    public String getCaptchaID() {
        return captchaID;
    }

    public String getAppID() {
        return appID;
    }

    public String getHashedCorrectAnswers() {
        return hashedCorrectAnswers;
    }

    public static String hashedAnswers(List<String> CorrectAnswers) {
        List<String> sortedAnswers = new ArrayList<>(CorrectAnswers);
        Collections.sort(sortedAnswers);
        return DigestUtils.md5DigestAsHex(sortedAnswers.toString().getBytes()).toUpperCase();
    }
    

    public String encrypt() {
       
            String tokenString = captchaID + ";" + appID + ";" + hashedCorrectAnswers + ";" + 
            expiryTime.toString();

            String cryptoToken = AES.encrypt(tokenString, SECRET_KEY);
            return cryptoToken;
        
    }

    public static Token decrypt(String encryptedToken){           
            String decryptedTokenString = AES.decrypt(encryptedToken, SECRET_KEY);
            if(decryptedTokenString == null)
                return null;
            String[] tokenFields = decryptedTokenString.split(";");
            if (tokenFields.length == 4) {
                String captchaID = tokenFields[0];
                String appID = tokenFields[1];
                String hashedCorrectAnswers = tokenFields[2];
                LocalDateTime expiryTime = LocalDateTime.parse(tokenFields[3]);
                return new Token(captchaID, appID, hashedCorrectAnswers, expiryTime);
            } else {
                return null;
            }



    }

}
