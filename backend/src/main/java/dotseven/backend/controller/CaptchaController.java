package dotseven.backend.controller;

import dotseven.backend.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dotseven.backend.dto.CaptchaRequest;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/captcha")
class CaptchaController {

	@Autowired
	private CaptchaService captchaService;

	// allow requesst from frontend
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/requestCaptcha")
	public ResponseEntity<CaptchaRequest> getNewCaptcha() {

		return ResponseEntity.ok().body(this.captchaService.getNewCaptcha());
	}

}
