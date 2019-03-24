package ru.croc.jws.messenger.common;

import java.util.Date;

public class Message {

	private final User user;
	private final String text;
	private final Date time;

	public Message(User user, String text) {
		this.user = user;
		this.text = text;
		this.time = new Date(); // now
	}

	public User getUser() {
		return user;
	}

	public String getText() {
		return text;
	}

	public Date getTime() {
		return time;
	}
}
