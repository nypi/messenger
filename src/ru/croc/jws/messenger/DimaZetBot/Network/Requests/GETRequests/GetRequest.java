package ru.croc.jws.messenger.DimaZetBot.Network.Requests.GETRequests;

import ru.croc.jws.messenger.DimaZetBot.JSON.JSON;
import ru.croc.jws.messenger.DimaZetBot.Network.Requests.Request;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

public abstract class GetRequest extends Request {
    @Override
    public Request build(String... args) {
        type = "GET";
        StringBuilder r = new StringBuilder(API).append(setMethodAndParams(args)).append("&access_token=").append(ACCESS_TOKEN).append("&v=").append(V);
        request = r.toString();
        System.out.println(Arrays.asList(request));
        return this;
    }

    @Override
    public void execute() throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(request).openConnection();
        connection.setRequestMethod(type);
        connection.connect();

        //int resp = connection.getResponseCode();
        //if (resp == 200) {
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        result = new JSON(sb.toString());
        //}
    }

    @Override
    public JSON getResult() {
        return result;
    }
}
