package dotseven.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CaptchaRequest {

	public CaptchaRequest() {};
	@JsonProperty
	private String criptoToken;
	@JsonProperty
	private String base64Image;
	@JsonProperty
	private List<String> captchaOptions;

}
