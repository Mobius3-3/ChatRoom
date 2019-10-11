package edu.udacity.java.nano.chat;

/**
 * WebSocket message model
 */
//import jdk.nashorn.internal.objects.annotations.Getter;
//import jdk.nashorn.internal.objects.annotations.Setter;
/**
 * WebSocket message model
 */

public class Message {
	// TODO: add message model.
	private String type;
	private String data;
	private String username;
	private int onlineCount;

	public Message() {};

	public Message(String username, int onlineCount, String type, String data) {
		this.username = username;
		this.onlineCount = onlineCount;
		this.type = type;
		this.data = data;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(int onlineCount) {
		this.onlineCount = onlineCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

//    public enum MessageType { OnOpen, OnMessage, OnClose};
}
