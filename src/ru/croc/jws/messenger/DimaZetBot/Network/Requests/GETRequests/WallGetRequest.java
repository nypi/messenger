package ru.croc.jws.messenger.DimaZetBot.Network.Requests.GETRequests;

public class WallGetRequest extends GetRequest {

    @Override
    protected StringBuilder setMethodAndParams(String... args) {
        method = "wall.get";
        String param = "domain";
        StringBuilder r = new StringBuilder(method).append("?").append(param).append("=").append(args[1]);
        return r;
    }
}