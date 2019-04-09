package ru.croc.jws.messenger.server;

import ru.croc.jws.messenger.common.Chat;
import ru.croc.jws.messenger.common.GroupChat;
import ru.croc.jws.messenger.common.Message;
import ru.croc.jws.messenger.common.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Server {

	private MessageRepository messages = new MessageRepository();

	private final int port;

	public Server() {
		this(7777);
	}

	public Server(int port) {
		this.port = port;
	}

	public void start() throws IOException {
		try (ServerSocket server = new ServerSocket(port)) {
			while (true) {
				try (Socket socket = server.accept()) {
					InputStream in = socket.getInputStream();

					Scanner s = new Scanner(in);
					String commandStr = s.nextLine();
					int command = Integer.parseInt(commandStr);
					switch (command) {
						case 0: // send message
							String username = s.nextLine();
							String text = s.nextLine();
							User user = new User(username);
							Message message = new Message(user, text);
							messages.addMessage(message);
							break;
						case 1:
							String timeStr = s.nextLine();
							Date time = new Date(Long.parseLong(timeStr));
							List<Message> result = messages.findMessagesAfter(time);

							// return result to a client
							OutputStream out = socket.getOutputStream();
							try (Writer w = new OutputStreamWriter(out)) {
								w.write(Integer.toString(result.size()));
								w.write("\n");
								for (Message msg : result) {
									w.write(msg.getUser().getName());
									w.write("\n");
									w.write(Long.toString(msg.getTime().getTime()));
									w.write("\n");
									String messageText = msg.getText().replaceAll("\n", " ");
									w.write(messageText);
									w.write("\n");
								}
							}
							break;
					}
				} catch (Exception e) {
					// command error
					// do nothing and continue to a next connection
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
	}
}
