package com.training.lab2a.other;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello World");

        // var client = HttpClient.newHttpClient();
        // var request = HttpRequest.newBuilder()
        //         .uri(URI.create("https://jsonplaceholder.typicode.com/users/1"))
        //         .header("accept", "application/json")
        //         .GET()
        //         .build();

        // var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // System.out.println(response.statusCode());
        // System.out.println(response.body());

        var json = """
            {
                "title": "foo", 
                "body": "bar", 
                "userId": 1, 
                "other": "value" 
            }
        """;

        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
            .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();
        
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}

