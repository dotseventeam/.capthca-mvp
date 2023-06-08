package dotseven.backend.controller;

import dotseven.backend.dto.UserDetailDto;
import dotseven.backend.security.UserDetailsImpl;
import dotseven.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/me")
    public ResponseEntity<?>  getCurrentUserInfo(@AuthenticationPrincipal UserDetailsImpl currentUser) {



        return ResponseEntity.ok().body(userService.fromUserDetailToDto(currentUser));
    }
}
