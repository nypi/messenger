package ru.croc.jws.messenger.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private static final String HOST = "localhost";
	private static final int PORT = 7777;

	public static void main(String[] args) throws IOException {

		Scanner s = new Scanner(System.in);
		System.out.print("Username: ");
		String username = s.nextLine();

		System.out.println();
		while (true) {
			System.out.print("> ");
			String message = s.nextLine();
			if (message == null || message.isEmpty())
				continue;
			if (message.equals(".")) // exit
				break;

			// send message
			message = message.replaceAll("\n", " ");
			try (Socket socket = new Socket(HOST, PORT)) {
				OutputStream out = socket.getOutputStream();
				try (Writer w = new OutputStreamWriter(out)) {
					w.write("0");
					w.write("\n");
					w.write(username);
					w.write("\n");
					w.write(message);
					w.write("\n");
				}
			}
		}
	}
}
