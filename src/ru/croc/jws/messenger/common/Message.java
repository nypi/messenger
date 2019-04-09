package ru.croc.jws.messenger.common;

import java.util.Date;

public class Message implements Comparable<Message> {

	private final User user;
	private final String text;
	private final Date time;

	public Message(User user, String text) {
		this(user, text, new Date());
	}

	public Message(User user, String text, Date time) {
		this.user = user;
		this.text = text;
		this.time = time;
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

	@Override
	public int compareTo(Message other) {
		return time.compareTo(other.time);
	}
}
