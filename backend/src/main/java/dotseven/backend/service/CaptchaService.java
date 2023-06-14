package dotseven.backend.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import dotseven.backend.dto.CaptchaRequest;

@Service
@RequiredArgsConstructor
public class CaptchaService {

	@Value("${app.captchaservice.location}")
	private String serviceUrl;

	@Value("${app.captchaservice.appid}")
	private String appId;

	@Value("${app.captchaservice.appsecret}")
	private String appSecret;

	private final RestTemplate captchaApi;


	public Optional<CaptchaRequest> getNewCaptcha()  {

		String url = this.serviceUrl + "/requestCaptcha";
		MultiValueMap<String, Object> payload = authenticatedPayload();
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(payload);

		ResponseEntity<CaptchaRequest> response;
		try{
			response = captchaApi.postForEntity(
					url,
					requestEntity,
					CaptchaRequest.class
			);
		} catch (Exception e) {
			return Optional.empty();
		}

		if (!response.getStatusCode().equals(HttpStatus.OK)) {
			return Optional.empty();
		}

		return Optional.of(response.getBody());
	}

	public Optional<Boolean> checkCaptcha(String captchaToken, List<String> captchaAnswers)  {

		String url = this.serviceUrl + "/checkAnswers";
		MultiValueMap<String, Object> payload = authenticatedPayload();
		payload.add("cryptoToken", captchaToken);
		for(String answer : captchaAnswers) {
			payload.add("userAnswers", answer);
		}
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(payload);

		ResponseEntity<Boolean> response;
		try{
			response = captchaApi.postForEntity(
					url,
					requestEntity,
					Boolean.class
			);
		} catch (Exception e) {
			return Optional.empty();
		}

		if (!response.getStatusCode().equals(HttpStatus.OK)) {
			return Optional.empty();
		}

		return Optional.of(response.getBody());
	}

	private MultiValueMap<String, Object> authenticatedPayload() {

		MultiValueMap<String, Object> payload = new LinkedMultiValueMap<>();
		payload.add("appId", this.appId);
		payload.add("appSecret", this.appSecret);

		return payload;
	}

}
