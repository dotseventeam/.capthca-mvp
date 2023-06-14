package dotseven.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dotseven.backend.controller.UserController;
import dotseven.backend.dto.UserDetailDto;
import dotseven.backend.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private JacksonTester<UserDetailDto> jacksonTester;

    @BeforeEach
    void setup() {

        // Arrange
        JacksonTester.initFields(this, new ObjectMapper().registerModule(new JavaTimeModule()));

        mvc = MockMvcBuilders.standaloneSetup(userController)
                .build();

    }

    @Test
    void getCurrentUserInfo()  throws Exception{

        // Arrange
        UserDetailsImpl impl = new UserDetailsImpl();
        impl.setUsername("User");
        impl.setPassword("Pass");
        impl.setAuthorities(List.of());
        UserDetailDto dto = new UserDetailDto("User", "First", "Last", 'X', LocalDate.EPOCH, "str");
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body(dto);

        when(userService.fromUserDetailToDto(Mockito.any(UserDetailsImpl.class))).thenReturn(dto);

        // Act
        MockHttpServletResponse response = mvc.perform(
                get("/api/users/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(response.getStatus(), expectedResponse.getStatusCode().value());
        assertEquals(response.getContentAsString(), jacksonTester.write(dto).getJson());

    }
}