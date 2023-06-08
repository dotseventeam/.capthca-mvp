package dotseven.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CaptchaRequest {
	@JsonProperty
	private String criptoToken;
	@JsonProperty
	private String base64Image;
	@JsonProperty
	private List<String> captchaOptions;

}
