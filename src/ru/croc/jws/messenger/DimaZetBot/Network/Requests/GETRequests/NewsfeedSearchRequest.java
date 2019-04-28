package ru.croc.jws.messenger.DimaZetBot.Network.Requests.GETRequests;

public class NewsfeedSearchRequest extends GetRequest {

    @Override
    protected StringBuilder setMethodAndParams(String... args) {
        method = "newsfeed.search";
        StringBuilder r = new StringBuilder(method).append("?q=");
        for (int i = 1; i < args.length-1; i++) {
            r.append(args[i]).append("_");
        }
        r.append(args[args.length-1]);
        return r;
    }
}