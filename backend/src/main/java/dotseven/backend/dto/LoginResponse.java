package dotseven.backend.dto;

public class LoginResponse {

	public final String jwtToken;

	public LoginResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getJwtToken() {
		return this.jwtToken;
	}
}
