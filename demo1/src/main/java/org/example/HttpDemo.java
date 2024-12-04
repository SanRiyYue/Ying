package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;

public class HttpDemo {

    static HttpClient httpClient = HttpClient.newBuilder().build();

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        String url = "http://www.baidu.com";
        String body = "username";
        HttpRequest request = HttpRequest.newBuilder(new URI(url))
                .header("User-Agent", "Java HttpClient").header("Accpet", "*/*") //设置Header
                .timeout(Duration.ofSeconds(5)) //设置超时
                .version(HttpClient.Version.HTTP_2).build(); //设置版本

        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
        //HTTP允许重复的Header，因此一个Header可以对应多个Value:
        Map<String, List<String>> headers = response.headers().map();
        for (String header : headers.keySet()) {
            System.out.println(header + ": " + headers.get(header).getFirst());
        }
        System.out.println(Arrays.toString(response.body()).substring(0, 1024) + "...");
    }
}
