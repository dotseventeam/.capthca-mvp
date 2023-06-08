package com.dotseven.captchaservice.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dotseven.captchaservice.captchatest.CaptchaTest;
import com.dotseven.captchaservice.captchatest.CaptchaTestRepository;
import com.dotseven.captchaservice.data.Captcha;
import com.dotseven.captchaservice.token.Token;
import com.dotseven.captchaservice.token.TokenRepository;

@Service
public class CaptchaService {
    private CaptchaTestRepository testBuffer;
    private TokenRepository tokenBuffer;
    private int counterId;

    @Autowired
    public CaptchaService(CaptchaTestRepository testBuffer, TokenRepository tokenBuffer) {
        this.testBuffer = testBuffer;
        this.tokenBuffer = tokenBuffer;
        this.counterId = 0;
    }

    public void initializateTestBuffer() {
        restockTestBuffer();
    }

    public Captcha getRandomCaptcha(String appID) {
        CaptchaTest captchaTestForCaptcha = this.testBuffer.getRandomTest();
        Token token = new Token(generateCaptchaId(), appID, captchaTestForCaptcha.getCorrectAnswers(),
                LocalDateTime.now().plus(5, ChronoUnit.MINUTES));
        List<String> answers = captchaTestForCaptcha.getCorrectAnswers();
        answers.addAll(testBuffer.getRandomAnswers(answers.get(0), answers.get(1)));
        Collections.shuffle(answers);
        return new Captcha(captchaTestForCaptcha.getBase64Image(), token.encrypt(), answers);
    }

    public boolean checkAnswers(Token token, List<String> userAnswers) {
        if (tokenBuffer.getToken(token.getAppID(), token.getCaptchaID()) == null) {
            this.tokenBuffer.save(token);
            Collections.sort(userAnswers);
            if (tokenBuffer.getToken(token.getAppID(), token.getCaptchaID()) != null &&
                    token.getHashedCorrectAnswers().equals(Token.hashedAnswers(userAnswers))) {
                return true;
            }
        }

        return false;
    }

    private String generateCaptchaId() {
        return String.valueOf(this.counterId++);
    }

    private void restockTestBuffer() {
        for (int i = 0; i < 100; i++) {
            CaptchaTest test = new CaptchaTest();
            this.testBuffer.save(test);
        }
    }
}
