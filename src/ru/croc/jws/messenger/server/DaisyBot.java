package ru.croc.jws.messenger.server;

import ru.croc.jws.messenger.common.Message;
import ru.croc.jws.messenger.common.User;

import java.util.Random;

public class DaisyBot implements Bot {

	private final User daisy = new User("daisy");
	private final Random rnd = new Random(System.currentTimeMillis());

	@Override
	public User getUser() {
		return daisy;
	}

	@Override
	public void onMessage(Message message, MessageRepository messageRepository) {
		String text = rnd.nextInt(7) == 0
				? "no!!"
				: "yes))";
		Message replyFromDaisy = new Message(
				daisy,
				Message.textWithMention(message.getUser(), text));

		// imitate delay: do not reply instantly
		int delaySeconds = 1 + rnd.nextInt(3);
		try {
			Thread.sleep(delaySeconds * 1000);
		} catch (InterruptedException e) {
			return;
		}
		messageRepository.addMessage(replyFromDaisy);
	}
}
