package exceptions;

import java.io.IOException;

public class IdontKnowException extends IOException {
	private String message;
	private String input;

	public IdontKnowException(String message, String input) {
		super();
		this.message = message;
		this.input = input;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	@Override
	public String toString() {
		return "IdontKnowException [message=" + message + ", input=" + input
				+ "]";
	}

}
