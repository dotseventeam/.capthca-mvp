package dotseven.backend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dotseven.backend.entity.User;
import dotseven.backend.service.UserService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userService.listusers().isEmpty()) {
            return;
        }
        USERS.forEach(user -> {
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
            userService.saveUser(user);
        });
    }

    private static final List<User> USERS = Arrays.asList(
            new User("admin", "admin", "Admin", "Lastname", LocalDate.EPOCH, "admin@mycompany.com", 'X', null),
            new User("user", "user", "User", "Lastname", LocalDate.EPOCH, "user@mycompany.com", 'X', null));
}