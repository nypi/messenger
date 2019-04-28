package ru.croc.jws.messenger.DimaZetBot.Network.Exeptions;

public class WrongCommandArgs extends Exception{
    private String why;

    public WrongCommandArgs(String why) {
        this.why = why;
    }

    @Override
    public String getMessage() {
        return why;
    }
}
