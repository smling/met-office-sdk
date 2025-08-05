package io.github.smling.metofficesdk.core;

import reactor.netty.http.client.HttpClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public abstract class BaseClient {
    protected HttpClient getHttpClient(SdkSettings sdkSettings) {
        if(Objects.isNull(sdkSettings.getApiKey())) {
            throw new IllegalArgumentException("API key could not be null");
        }
        return HttpClient.create()
                .baseUrl(sdkSettings.getBaseUrl())
                .headers(headers -> {
                    headers.add("accept", "application/json");
                    headers.add("apikey", sdkSettings.getApiKey());
                });
    }

    protected String buildQuery(Map<String, String> params) {
        if (params.isEmpty()) return "";
        StringBuilder query = new StringBuilder("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (query.length() > 1) query.append("&");
            query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            query.append("=");
            query.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return query.toString();
    }
}
