package ru.croc.jws.messenger.DimaZetBot.Network.Requests;

import ru.croc.jws.messenger.DimaZetBot.JSON.JSON;

import java.io.IOException;

public abstract class Request {
    protected static final String API = "https://api.vk.com/method/";
    protected static final String ACCESS_TOKEN = "60b897cc60b897cc60b897cc3e60d1fb4c660b860b897cc3c2b5a4b6c136e2fae866644";
    protected static final String V = "5.95";
    protected String type;
    protected String method;
    protected String request;
    protected JSON result;

    public abstract Request build(String... args);
    public abstract void execute() throws IOException;

    public String getType() {return type;}
    public String getMethod() {return method;}
    public String getRequest() {return request;}
    public abstract JSON getResult();
    protected abstract StringBuilder setMethodAndParams(String... args);
}

