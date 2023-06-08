package com.dotseven.captchaservice.authentication;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            AppRepository repository) {
                return agrs -> {
                    App app1 = new App("123456789", "123456789");
                    App app2 = new App("223456789", "223456789");
                    repository.saveAll(List.of(app1,app2));
                };
                
            }
}
