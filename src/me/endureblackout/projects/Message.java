package me.endureblackout.projects;

public class Message {
	protected String message;
	protected int code;
	
	public Message(String message, int code) {
		this.message = message;
		this.code = code;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
