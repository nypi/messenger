package ru.croc.jws.messenger.DimaZetBot;

import ru.croc.jws.messenger.DimaZetBot.JSON.JSON;
import ru.croc.jws.messenger.DimaZetBot.JSON.JSONParser;
import ru.croc.jws.messenger.DimaZetBot.Network.Requests.Request;
import ru.croc.jws.messenger.DimaZetBot.Network.Requests.RequestBuilder;
import ru.croc.jws.messenger.common.Message;
import ru.croc.jws.messenger.common.User;
import ru.croc.jws.messenger.server.Bot;
import ru.croc.jws.messenger.server.MessageRepository;

import java.util.Arrays;

public class DimaZetBot implements Bot {

    private final User bot = new User("DimaZet");

    @Override
    public User getUser() {
        return bot;
    }

    @Override
    public void onMessage(Message message, MessageRepository messageRepository) {
        String text = message.getText();
        String[] command = text.substring(text.indexOf(' ')+1).split(" ");

        System.out.println(Arrays.asList(command));
        String response = "[empty]";

        RequestBuilder requestBuilder = new RequestBuilder();
        Request request;
        try {
            request = requestBuilder.build(command);
            System.out.println(Arrays.asList(request.getType(), request.getMethod(), request.getRequest()));
            request.execute();
            System.out.println(Arrays.asList(request.getResult()));
        } catch (Throwable e) {
            onWrongCommand(e, message, messageRepository);
            return;
        }

        JSONParser parser = new JSONParser();
        JSON json = request.getResult();
        System.out.println(Arrays.asList(json.toString()));
        if (json != null){
            response = parser.parse(json, request.getMethod());
            System.out.println(Arrays.asList(response));
        }

        Message answer = new Message(bot, response);
        messageRepository.addMessage(answer);
    }

    private void onWrongCommand(Throwable e, Message message, MessageRepository messageRepository) {
        Message normal = new Message(bot,e.getMessage());
        Message fake = new Message(message.getUser(), "я дурачок и не умею пользоваться ботом(");
        messageRepository.addMessage(normal);
        messageRepository.addMessage(fake);
    }

}
