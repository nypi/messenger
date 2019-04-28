package ru.croc.jws.messenger.DimaZetBot.Network.Exeptions;

public class WrongCommandMethod extends Exception {
    private String method;

    public WrongCommandMethod(String method) {
        this.method = method;
    }

    @Override
    public String getMessage() {
        return "не знаю команды \"" + method + "\"";
    }
}
