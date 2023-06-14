package com.dotseven.captchaservice.captchatest;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaptchaTestConfig {
    @Bean
    CommandLineRunner commandLineRunnerCaptcha(
            CaptchaTestRepository repository) {

        return agrs -> {
            for (int i = 0; i < 100; i++) {
                CaptchaTest captchaTest = new CaptchaTest();
                repository.saveAll(List.of(captchaTest));
            }
        };

    }
}
