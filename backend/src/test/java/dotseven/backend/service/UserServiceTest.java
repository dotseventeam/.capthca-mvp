package dotseven.backend.service;

import dotseven.backend.dto.UserDetailDto;
import dotseven.backend.entity.User;
import dotseven.backend.repository.UserRepository;
import dotseven.backend.security.UserDetailsImpl;
import jakarta.validation.constraints.AssertTrue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class UserServiceTest {

    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private UserService userService;

    @BeforeEach
    void setup() {
        // Arrange
        userService = new UserService(userRepository);
    }


    @Test
    void findByUsername_found() {

        //Arrange
        String username = "admin";

        User mockUser = new User(Mockito.anyString(), "", "");

        when(userRepository.findUserByUsernam(username)).thenReturn(mockUser);

        // Act
        Optional<User> result = userService.findByUsername(username);

        // Assert
        assertEquals(result, Optional.of(mockUser));

    }

    @Test
    void findByUsername_notFound() {

        // Arrange
        when(userRepository.findUserByUsernam(Mockito.anyString())).thenReturn(null);

        // Act
        Optional<User> result = userService.findByUsername("admin");

        // Assert
        assertEquals(result, Optional.empty());
    }

    @Test
    void listusers() {

        // Arrange
        User mockUser1 = new User("user", "upass", "uFirst", "uLast", LocalDate.EPOCH,  "user@email.xyz", 'M', "str");
        User mockUser2 = new User("admin", "apass", "aFirst", "aLast", LocalDate.EPOCH.plusDays(1),  "admin@email.xyz", 'F', "string");
        List<User> mockUserList = List.of(mockUser1, mockUser2);

        when(userRepository.findAll()).thenReturn(mockUserList);

        // Act
        List<User> result = userService.listusers();

        // Assert
        assertEquals(result, mockUserList);

    }

    @Test
    void saveUser() {

        // Arrange
        User mockUser1 = new User("user", "upass", "uFirst", "uLast", LocalDate.EPOCH,  "user@email.xyz", 'M', "str");

        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        // Act
        User result = userService.saveUser(mockUser1);

        // Assert
        assertEquals(result, mockUser1);
    }

    @Test
    void fromUserDetailToDto() {

        // Arrange
        UserDetailsImpl mockUserDetail = new UserDetailsImpl();
        mockUserDetail.setUsername("user");
        mockUserDetail.setPassword("upass");
        mockUserDetail.setAuthorities(null);

        User mockUser = new User(
                "user",
                "upass",
                "uFirst",
                "uLast",
                LocalDate.EPOCH,
                "user@email.xyz",
                'M',
                "str");

        UserDetailDto expectedResult = new UserDetailDto("user",
                "uFirst",
                "uLast",
                'M',
                LocalDate.EPOCH,
                "str"
        );

        when(userRepository.findUserByUsernam(mockUserDetail.getUsername())).thenReturn(mockUser);

        // Act
        UserDetailDto result = userService.fromUserDetailToDto(mockUserDetail);

        // Assert
        assertEquals(result, expectedResult);
    }
}