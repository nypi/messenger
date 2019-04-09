package ru.croc.jws.messenger.server;

import ru.croc.jws.messenger.common.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MessageRepository {

	// list of all messages sorted by time
	private List<Message> messages = new ArrayList<>();
	private Comparator<Message> comparator = new MessageComparator();

    public void addMessage(Message message) {
		int i = Collections.binarySearch(messages, message, comparator);
		if (i < 0) {
			i = -i - 1;
		}
		messages.add(i, message);
    }

    public List<Message> findMessagesAfter(Date time) {
    	List<Message> result = new ArrayList<>();
		ListIterator<Message> it = messages.listIterator(messages.size());
		while (it.hasPrevious()) {
			Message message = it.previous();
			if (message.getTime().after(time)) {
				result.add(message);
			}
		}
		Collections.reverse(result);
		return result;
    }

    static class MessageComparator implements Comparator<Message> {

		@Override
		public int compare(Message a, Message b) {
			return a.getTime().compareTo(b.getTime());
		}
	}
}
