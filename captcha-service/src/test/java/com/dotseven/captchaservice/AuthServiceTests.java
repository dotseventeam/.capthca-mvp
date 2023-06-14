package com.dotseven.captchaservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dotseven.captchaservice.authentication.App;
import com.dotseven.captchaservice.authentication.AppRepository;
import com.dotseven.captchaservice.services.AuthService;

public class AuthServiceTests {
    
    private AppRepository appBuffer = mock(AppRepository.class);

    
    private App app = mock(App.class);

    private AuthService authService = new AuthService(appBuffer);

    private String appId ;
    private String appSecret ;
        
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        appId = "2344444444";
        appSecret = "2344444444";
        
    }

    @Test
    public void authenticateTest(){
        when(appBuffer.getApp(appId)).thenReturn(app);
        when(app.getAppSecret()).thenReturn(appSecret);
        assertTrue( authService.authenticate(appId, appSecret));
        assertFalse(authService.authenticate(appId, appSecret+"4"));
    }
}
