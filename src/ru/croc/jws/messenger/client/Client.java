package ru.croc.jws.messenger.client;

import ru.croc.jws.messenger.common.Message;
import ru.croc.jws.messenger.common.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Client {

	private final String host;
	private final int port;

	public Client() {
		this("localhost", 7777);
	}

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void sendMessage(Message message) throws IOException {
		if (message == null)
			throw new IllegalArgumentException("message is null");

		// send message
		try (Socket socket = new Socket(host, port)) {
			OutputStream out = socket.getOutputStream();
			try (Writer w = new OutputStreamWriter(out)) {
				w.write("0");
				w.write("\n");
				w.write(message.getUser().getName());
				w.write("\n");
				String messageText = message.getText().replaceAll("\n", " ");
				w.write(messageText);
				w.write("\n");
			}
		}
	}

	public List<Message> getMessages(Date time) throws IOException {
		try (Socket socket = new Socket(host, port)) {
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();

			List<Message> messages = new ArrayList<>();
			try (Writer w = new OutputStreamWriter(out)) {
				w.write("1");
				w.write("\n");
				w.write(Long.toString(time.getTime()));
				w.write("\n");
				w.flush();

				Scanner s = new Scanner(in);
				String numMessagesStr = s.nextLine();
				int numMessages = Integer.parseInt(numMessagesStr);
				for (int i = 0; i < numMessages; i++) {
					String username = s.nextLine();
					String timeStr = s.nextLine();
					Date messageTime = new Date(Long.parseLong(timeStr));
					String text = s.nextLine();
					User user = new User(username);
					Message message = new Message(user, text, messageTime);
					messages.add(message);
				}
			}
			return messages;
		}
	}
}
