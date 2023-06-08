package dotseven.backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import dotseven.backend.service.CaptchaService;
import dotseven.backend.dto.AuthRequest;
import dotseven.backend.dto.ErrorResponse;
import dotseven.backend.dto.LoginResponse;
import dotseven.backend.entity.User;
import dotseven.backend.security.TokenProvider;
import dotseven.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private CaptchaService captchaService;

	@Autowired
	private TokenProvider tokenProvider;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/authenticate")
	public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {

 		if (!captchaService.checkCaptcha(authRequest.getCaptchaToken(), authRequest.getCaptchaAnswers())) {
			ErrorResponse response = new ErrorResponse("Captcha verification failed");
			return ResponseEntity.status(422).body(response); // Unprocessable entity -> the captcha verification is
																// unprocessable
		}

		String token = authenticateAndGetToken(authRequest.getUsername(), authRequest.getPasswordHash());

		LoginResponse response = new LoginResponse(token);

		return ResponseEntity.ok().body(response);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/logout")
	public ResponseEntity<String> logout() {
		// authService.revokeSession(sessionId);
		return ResponseEntity.ok().body("hey, i'mm a string");
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/secured")
	public ResponseEntity<String> secured() {
		// authService.revokeSession(sessionId);
		return ResponseEntity.ok().body("hey, i'm secured");
	}

	private String authenticateAndGetToken(String username, String password) {
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));
		return tokenProvider.generateToken(authentication);
	}

	@GetMapping("users")
	public ResponseEntity<List<User>> listusers() {
		return ResponseEntity.ok().body(userService.listusers());
	}

}
