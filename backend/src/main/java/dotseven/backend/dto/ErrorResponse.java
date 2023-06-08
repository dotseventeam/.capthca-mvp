package dotseven.backend.dto;

public class ErrorResponse {

	private final String errorMessage;

	public ErrorResponse(String message) {
		this.errorMessage = message;
	}

	public String getMessage() {
		return this.errorMessage;
	}

};
