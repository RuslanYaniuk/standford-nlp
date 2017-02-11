package client.config;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.nio.charset.Charset;

@Configuration
public class HttpClient {

    public static String convertResponseToString(CloseableHttpResponse response) throws IOException {
        String result = IOUtils.toString(response.getEntity().getContent(), getCharset(response).displayName());
        response.getEntity().getContent().close();
        return result;
    }

    public static Charset getCharset(CloseableHttpResponse response) {
        HttpEntity responseEntity = response.getEntity();
        ContentType contentType = ContentType.getOrDefault(responseEntity);
        Charset cs = contentType.getCharset();
        return cs == null ? Charset.defaultCharset() : cs;
    }

    @Bean
    public CloseableHttpClient closeableHttpClient() {
        return getConfiguredHttpClient();
    }

    private CloseableHttpClient getConfiguredHttpClient() {
        CookieHandler.setDefault(new CookieManager());

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig
                        .custom()
                        .setCircularRedirectsAllowed(true)
                        .build())
                .build();
    }
}
