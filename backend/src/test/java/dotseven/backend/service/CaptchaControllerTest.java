package dotseven.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dotseven.backend.controller.CaptchaController;
import dotseven.backend.dto.CaptchaRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
class CaptchaControllerTest {

    private MockMvc mvc;
    @Mock
    private CaptchaService captchaService;

    @InjectMocks
    private CaptchaController captchaController;

    private JacksonTester<CaptchaRequest> jacksonTester;

    @BeforeEach
    void setUp() {

        // Arrange
        JacksonTester.initFields(this, new ObjectMapper().registerModule(new JavaTimeModule()));

        mvc = MockMvcBuilders.standaloneSetup(captchaController)
                .build();
    }

    @Test
    void getNewCaptcha() throws Exception {

        // Arrange
        CaptchaRequest mockCaptcha = new CaptchaRequest("token", "image", List.of("ans1", "ans2"));
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body(mockCaptcha);

        when(captchaService.getNewCaptcha()).thenReturn(Optional.of(mockCaptcha));

        // Act
        MockHttpServletResponse response = mvc.perform(
                get("/api/captcha/requestCaptcha")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(response.getStatus(), expectedResponse.getStatusCode().value());
        assertEquals(response.getContentAsString(), jacksonTester.write(mockCaptcha).getJson());
    }
}