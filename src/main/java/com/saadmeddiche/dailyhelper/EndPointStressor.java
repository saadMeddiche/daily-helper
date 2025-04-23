package com.saadmeddiche.dailyhelper;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class EndPointStressor {

    private final static String ENDPOINT = "http://localhost:8080/claim";

    private final static String AUTHENTICATION_ENDPOINT = "http://localhost:8080/login";

    private String AUTHORIZATION;

    private final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) {

        EndPointStressor endPointStressor = new EndPointStressor();

        endPointStressor.stress();

    }

    public EndPointStressor() {

        HttpRequest authenticationRequest = createAuthenticationRequest();

        try {
            HttpResponse<String> response = client.send(authenticationRequest, HttpResponse.BodyHandlers.ofString());
            AUTHORIZATION = "Bearer " + response.body();
            log.info("Authorization token: {}", AUTHORIZATION);
        } catch (IOException | InterruptedException e) {
            log.error("Error while sending authentication request: {}", e.getMessage());
        }

    }

    public void stress() {

        HttpRequest request = createRequest();

        stress(request, 100);

    }

    private void stress(HttpRequest request, int count) {

        try(ExecutorService executor = Executors.newFixedThreadPool(count)) {

            for (int i = 0; i < count; i++) {
                executor.submit(() -> {
                    try {
                        HttpResponse<String> response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
                        log.info(response.body());
                    } catch (IOException | InterruptedException e) {
                        log.error("Error while sending request: {}", e.getMessage());
                    }
                });
            }

        } catch (Exception e) {
            log.error("Error while creating thread pool: {}", e.getMessage());
        }

    }

    private HttpRequest createRequest() {
        return HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .header("Content-Type", "application/json")
                .header("Authorization" , AUTHORIZATION)
                .build();
    }

    private HttpRequest createAuthenticationRequest() {
        return HttpRequest.newBuilder()
                .uri(URI.create(AUTHENTICATION_ENDPOINT))
                .POST(HttpRequest.BodyPublishers.ofString("{\"username\": \"user1\", \"password\": \"password\"}"))
                .header("Content-Type", "application/json")
                .build();
    }


}
