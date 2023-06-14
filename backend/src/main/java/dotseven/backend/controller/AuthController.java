package dotseven.backend.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;

	private final AuthenticationManager authManager;

	private final CaptchaService captchaService;

	private final TokenProvider tokenProvider;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/authenticate")
	public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {

		Optional<Boolean> captchaValidationResult = captchaService.checkCaptcha(authRequest.getCaptchaToken(), authRequest.getCaptchaAnswers());
		if (captchaValidationResult.isEmpty() || !captchaValidationResult.get().booleanValue()) {
			ErrorResponse response = new ErrorResponse("Captcha verification failed");
			return ResponseEntity.status(422).body(response); // Unprocessable entity -> the captcha verification is
																// unprocessable
		}

		String token = authenticateAndGetToken(authRequest.getUsername(), authRequest.getPasswordHash());

		LoginResponse response = new LoginResponse(token);

		return ResponseEntity.ok().body(response);
	}


	private String authenticateAndGetToken(String username, String password) {
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));
		return tokenProvider.generateToken(authentication);
	}

}
