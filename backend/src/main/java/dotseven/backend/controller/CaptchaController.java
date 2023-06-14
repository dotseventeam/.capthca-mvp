package dotseven.backend.controller;

import dotseven.backend.dto.ErrorResponse;
import dotseven.backend.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dotseven.backend.dto.CaptchaRequest;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {

	@Autowired
	private CaptchaService captchaService;

	// allow requesst from frontend
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/requestCaptcha")
	public ResponseEntity<?> getNewCaptcha() {

		Optional<CaptchaRequest> newCaptcha = this.captchaService.getNewCaptcha();

		if(newCaptcha.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("Unable to get new Captcha"));
		}

		return ResponseEntity.ok().body(newCaptcha.get());
	}

}
