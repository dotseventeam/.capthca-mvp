package dotseven.backend.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class AuthRequest {

	@NotNull
	private final String username;

	@NotNull
	private final String passwordHash;

	@NotNull
	private final String captchaToken;

	@NotNull
	private final List<String> captchaAnswers;

	public AuthRequest(String username,
			String passwordHash,
			String captchaToken,
			List<String> captchaAnswers) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.captchaToken = captchaToken;
		this.captchaAnswers = captchaAnswers;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public String getCaptchaToken() {
		return this.captchaToken;
	}

	public List<String> getCaptchaAnswers() {
		return this.captchaAnswers;
	}
}
