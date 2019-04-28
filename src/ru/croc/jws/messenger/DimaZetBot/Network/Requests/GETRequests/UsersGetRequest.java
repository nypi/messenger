package ru.croc.jws.messenger.DimaZetBot.Network.Requests.GETRequests;

public class UsersGetRequest extends GetRequest {
    @Override
    protected StringBuilder setMethodAndParams(String... args) {
        method = "users.get";
        String param = "user_ids";
        StringBuilder r = new StringBuilder(method).append("?").append(param).append("=").append(args[1]);
        return r;
    }
}