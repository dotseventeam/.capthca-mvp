package dotseven.backend.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class AuthRequest {


	public AuthRequest() {};
	@JsonProperty
	@NotNull
	private  String username;

	@JsonProperty
	@NotNull
	private  String passwordHash;

	@JsonProperty
	@NotNull
	private  String captchaToken;

	@JsonProperty
	@NotNull
	private  List<String> captchaAnswers;

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
