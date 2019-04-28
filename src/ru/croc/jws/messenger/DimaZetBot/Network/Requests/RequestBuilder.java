package ru.croc.jws.messenger.DimaZetBot.Network.Requests;

import ru.croc.jws.messenger.DimaZetBot.Network.Exeptions.WrongCommandArgs;
import ru.croc.jws.messenger.DimaZetBot.Network.Requests.GETRequests.UsersGetRequest;
import ru.croc.jws.messenger.DimaZetBot.Network.Requests.GETRequests.WallGetRequest;
import ru.croc.jws.messenger.DimaZetBot.Network.Requests.GETRequests.NewsfeedSearchRequest;
import ru.croc.jws.messenger.DimaZetBot.Network.Exeptions.WrongCommandMethod;

import java.util.HashMap;

public class RequestBuilder {


    HashMap<String, Request> methods = new HashMap<>();
    private void initializeMethods() {
        methods.put("группа", new WallGetRequest());
        methods.put("пользователь", new UsersGetRequest());
        methods.put("поиск", new NewsfeedSearchRequest());
    }

    public Request build(String... args) throws WrongCommandMethod, WrongCommandArgs {
        String command = args[0];
        if (!methods.containsKey(command))
            throw new WrongCommandMethod(command);
        if (args.length < 2)
            throw new WrongCommandArgs("недостаточно понятно");
        return methods.get(command).build(args);
    }

    public RequestBuilder() {
        initializeMethods();
    }
}
