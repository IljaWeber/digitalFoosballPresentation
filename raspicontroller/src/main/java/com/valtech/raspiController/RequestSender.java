package com.valtech.raspiController;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class RequestSender {
    public int sendRaise(int teamNumber) throws IOException {
        String url = "http://192.168.123.51:8888/digitalfoosball/raise";

        HttpPost request = new HttpPost(url);

        StringEntity myEntity = new StringEntity(String.valueOf(teamNumber),
                ContentType.create("application/json", "UTF-8"));

        request.setEntity(myEntity);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);

        return response.getStatusLine().getStatusCode();
    }
}
