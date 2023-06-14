package com.dotseven.captchaservice;


import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


import com.dotseven.captchaservice.authentication.App;

@SpringBootTest
public class AppTest {

        String appID = "myAppID";
        String appSecret = "myAppSecret";
        App app = new App(appID, appSecret);

        
    @Test
    public void testGetAppID() {
        assertEquals(appID, app.getAppID());
    }

    @Test
    public void testGetAppSecret() {
        String appID = "myAppID";
        String appSecret = "myAppSecret";
        App app = new App(appID, appSecret);

        assertEquals(appSecret, app.getAppSecret());
    }
}
