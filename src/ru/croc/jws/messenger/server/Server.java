package ru.croc.jws.messenger.server;

import ru.croc.jws.messenger.common.Chat;
import ru.croc.jws.messenger.common.GroupChat;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Server {

	private static final int PORT = 7777;

	public static void main(String[] args) throws IOException {

		try (ServerSocket server = new ServerSocket(PORT)) {
			while (true) {
				try (Socket socket = server.accept()) {
					InputStream in = socket.getInputStream();

					Scanner s = new Scanner(in);
					String commandStr = s.nextLine();
					int command = Integer.parseInt(commandStr);
					switch (command) {
						case 0: // send message
							String username = s.nextLine();
							String message = s.nextLine();

							Date now = new Date();
							System.out.println(now + " [" + username + "]: " + message);
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
}
