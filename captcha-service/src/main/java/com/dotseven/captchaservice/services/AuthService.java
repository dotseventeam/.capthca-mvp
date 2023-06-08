package com.dotseven.captchaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dotseven.captchaservice.authentication.App;
import com.dotseven.captchaservice.authentication.AppRepository;

@Service
public class AuthService {
    private AppRepository appBuffer;

    @Autowired
    public AuthService(AppRepository appBuffer) {
        this.appBuffer = appBuffer;
    }

    public boolean authenticate(String appID, String appSecret){
        App app = appBuffer.getApp(appID);
        return (app != null && appSecret.equals(app.getAppSecret()));
    }
    
}
