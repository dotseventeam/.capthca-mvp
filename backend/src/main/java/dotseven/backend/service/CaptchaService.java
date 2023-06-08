package dotseven.backend.service;

import java.nio.charset.Charset;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import dotseven.backend.dto.CaptchaRequest;
import dotseven.backend.dto.CaptchaRequestDto;
import dotseven.backend.dto.CaptchaVerificationDto;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@RequiredArgsConstructor
public class CaptchaService {

	@Value("${app.captchaservice.location}")
	private String serverUrl;

	@Value("${app.captchaservice.appid}")
	private String appid;

	@Value("${app.captchaservice.appsecret}")
	private String appsecret;

	private final RestTemplate restTemplate;

	public boolean checkCaptcha(String token, List<String> answers) {

		String uri = this.serverUrl + "/checkAnswers";
		HttpHeaders headers = new HttpHeaders();
		// headers.setBearerAuth(this.serverToken);
		/*
		 * headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		 * 
		 * CaptchaVerificationDto requestBody = new CaptchaVerificationDto(
		 * this.appid,
		 * this.appsecret,
		 * token,
		 * answers
		 * );
		 * HttpEntity<CaptchaVerificationDto> reqeustEntity = new
		 * HttpEntity<>(requestBody, headers);
		 */
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
		formData.add("appId", this.appid);
		formData.add("appSecret", this.appsecret);
		formData.add("cryptoToken", token);
		for (String value : answers) {
			formData.add("userAnswers", value);
		}
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

		ResponseEntity<Boolean> response = restTemplate.postForEntity(uri, requestEntity, Boolean.class);

		if (response.getStatusCode().equals(HttpStatus.UNAUTHORIZED))
			throw new RuntimeException("Expired Credentials");

		if (response.getStatusCode().equals(HttpStatus.OK))
			return response.getBody().booleanValue();// response.getBody().booleanValue();

		throw new RuntimeException("Unreachable Service");

	}

	public CaptchaRequest getNewCaptcha() {

		String uri = this.serverUrl + "/requestCaptcha";
		HttpHeaders headers = new HttpHeaders();
		// headers.setBearerAuth(this.serverToken);
		/*
		 * headers.setContentType(MediaType.APPLICATION_JSON);
		 * 
		 * CaptchaRequestDto requestBody = new CaptchaRequestDto(
		 * this.appid,
		 * this.appsecret
		 * );
		 * HttpEntity<CaptchaRequestDto> requestEntity = new HttpEntity<>(requestBody,
		 * headers);
		 */
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("appId", this.appid);
		formData.add("appSecret", this.appsecret);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

		ResponseEntity<CaptchaRequest> response = restTemplate.postForEntity(uri, requestEntity, CaptchaRequest.class);

		if (response.getStatusCode().equals(HttpStatus.OK))
			return response.getBody();

		if (response.getStatusCode().equals(HttpStatus.UNAUTHORIZED))
			throw new RuntimeException("Expired Credentials");

		throw new RuntimeException("Unreachable Service");

	}

}
