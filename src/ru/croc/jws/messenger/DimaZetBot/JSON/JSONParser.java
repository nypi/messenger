package ru.croc.jws.messenger.DimaZetBot.JSON;

import ru.croc.jws.messenger.DimaZetBot.Network.Exeptions.WrongCommandArgs;
import ru.croc.jws.messenger.DimaZetBot.Network.Requests.RequestBuilder;
import ru.croc.jws.messenger.DimaZetBot.Network.Requests.Request;
import ru.croc.jws.messenger.DimaZetBot.Network.Exeptions.WrongCommandMethod;

import java.io.IOException;
import java.util.Date;

public class JSONParser {

    public String parse(JSON json, String type){
        String responce;
        switch (type) {
            case "wall.get":
                responce = parseWallPost(json.toString());
                break;
            case "users.get":
                responce = parseUsersInfo(json.toString());
                break;
            case "newsfeed.search":
                responce = parseSearchedPost(json.toString());
                break;
            default:
                responce = "JSON parse Error";
                break;
        }
        return clear(responce);
    }

    private String clear(String responce) {
        return responce
                .replaceAll("\\\\n", " ")
                .replaceAll("\\\\/", "/")
                .replaceAll("\\\\\"", "\"")
                .replaceAll("\\\\\'", "\'");
    }

    private String parseSearchedPost(String string) {
        String from_id = getStringParam("from_id",string);

        try {
            Request getAuthor = new RequestBuilder().build("пользователь", from_id);
            getAuthor.execute();
            JSON author = getAuthor.getResult();
            String userName = parseUsersInfo(author.toString());
            String date = new Date(getIntParam("date", string) * 1000L).toString();
            String text = parseWallPost(string);
            return "[" + userName + " " + date + "] " + text;
        } catch (WrongCommandMethod | WrongCommandArgs | IOException e) {
            return e.getMessage();
        }
    }

    private String parseUsersInfo(String string) {
        return getStringParam("first_name", string) + " " + getStringParam("last_name", string);
    }

    private String parseWallPost(String string) {
        String text = getStringParam("text", string);
        return text.isEmpty()?"[blank]":text;
    }

    private String getStringParam(String param, String json) {
        String substr;
        param = "\"" + param + "\":";
        int from = json.indexOf(param) + param.length() + 1;
        int to = json.indexOf("\",",from);
        substr = json.substring(from,to);
        return substr;
    }

    private int getIntParam(String param, String json) {
        String substr;
        param = "\"" + param + "\":";
        int from = json.indexOf(param) + param.length();
        int to = json.indexOf(",",from);
        substr = json.substring(from,to);
        return Integer.valueOf(substr);
    }
}