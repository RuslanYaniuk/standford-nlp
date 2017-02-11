package client.controllers;

import client.services.TextService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static client.config.HttpClient.convertResponseToString;

@Component
public class TextController {

    public static final String URL = "https://api.trustedshops.com/rest/public/v2/shops/{key}/reviews.json";
    public static final String KEY = "X943E74C50230B6DE76F473502940BB91";

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private TextService textService;

    public void parseNames() throws IOException, ClassNotFoundException {
        String url = URL.replace("{key}", KEY);
        HttpGet reviewsPage = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(reviewsPage);
        List<String> comments = textService.parseCommentsWithNames(convertResponseToString(response));
        comments.forEach(System.out::println);
    }
}
