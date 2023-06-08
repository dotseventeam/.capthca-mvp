package com.dotseven.captchaservice.authentication;

import javax.persistence.*;

@Entity
@Table(name = "app")
public class App {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "app_id", nullable = false)
    private String appID;
    @Column(name = "app_secret", nullable = false)
    private String AppSecret;

    public App(){}
    public App(String appID, String appSecret) {
        this.appID = appID;
        AppSecret = appSecret;
    }

    public String getAppID() {
        return appID;
    }

    public String getAppSecret() {
        return AppSecret;
    }
}
