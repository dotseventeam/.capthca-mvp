package dotseven.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CaptchaRequestDto {

    @JsonProperty
    private final String appId;
    @JsonProperty
    private final String appSecret;

    public CaptchaRequestDto(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }
}