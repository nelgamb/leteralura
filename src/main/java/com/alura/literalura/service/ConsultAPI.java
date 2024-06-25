package com.alura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultAPI {
    public String getDataFromAPI(String apiURL) {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(apiURL))
            .build();
        HttpResponse<String> response = null;
        String json;
        try {
            response = client
                    .send(request,HttpResponse.BodyHandlers.ofString());

            json = response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return json;
    } // end getDataFromAPI()
}// end ConsultAPI class
