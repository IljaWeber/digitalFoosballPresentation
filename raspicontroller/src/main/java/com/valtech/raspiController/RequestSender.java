package com.valtech.raspiController;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class RequestSender {
    public void sendRaise(int teamNumber) throws IOException {
        String url = "https://digital-football.valtech.io/api/raise";

        HttpPost request = new HttpPost(url);

        StringEntity myEntity = new StringEntity(String.valueOf(teamNumber),
                                                 ContentType.create("application/json", "UTF-8"));

        request.setEntity(myEntity);

        String authHeader = "Basic ZGlnaXRhbC5mb290YmFsbDpVUnlnIyM0fSMpSzpcXHIyflErS301KSM/NURXM0cnXiQ9b3I6OyY8K31gJ1dVP01QeA==";
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);

        response.getStatusLine().getStatusCode();
    }
}
