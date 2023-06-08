package com.dotseven.captchaservice.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.dotseven.captchaservice.data.Captcha;
import com.dotseven.captchaservice.services.AuthService;
import com.dotseven.captchaservice.services.CaptchaService;
import com.dotseven.captchaservice.token.Token;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestController
public class ServiceController {

    CaptchaService captchaService;
    AuthService authService;

    @Autowired
    public ServiceController(CaptchaService captchaService, AuthService authService) {
        this.captchaService = captchaService;
        this.authService = authService;
    }

    @CrossOrigin(origins = "http://localhost:5000")
    @PostMapping("/requestCaptcha")
    public Captcha requestCaptcha(@RequestParam String appId, @RequestParam String appSecret) {

        if (appId != null && appSecret != null) {
            if (authService.authenticate(appId, appSecret))
                return captchaService.getRandomCaptcha(appId);
            else
                throw new UnauthorizedException();
        }
        return null;
    }

    @CrossOrigin(origins = "http://localhost:5000")
    @PostMapping("/checkAnswers")
    public boolean checkAnswers(@RequestParam String appId, @RequestParam String appSecret,
            @RequestParam String cryptoToken, @RequestParam List<String> userAnswers) {

        if (appId != null && appSecret != null && cryptoToken != null && !userAnswers.isEmpty()) {
            if (authService.authenticate(appId, appSecret)) {
                Token token = Token.decrypt(cryptoToken);
                // System.out.println("\n\ntoken nullo: "+token == null);
                if (token == null || token.getExpiryTime().isBefore(LocalDateTime.now())) {
                    return false;
                }
                return captchaService.checkAnswers(token, userAnswers);
            } else {
                throw new UnauthorizedException();
            }

        }
        return false;

    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleUnauthorizedException() {
        String errorMessage = "Autenticazione fallita. Accesso non autorizzato.";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    private static class UnauthorizedException extends RuntimeException {
    }

}
