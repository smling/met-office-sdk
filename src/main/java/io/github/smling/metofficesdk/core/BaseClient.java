package io.github.smling.metofficesdk.core;

import reactor.netty.http.client.HttpClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public abstract class BaseClient {
    protected HttpClient getHttpClient(SdkSettings sdkSettings) {
        return HttpClient.create()
                .baseUrl(sdkSettings.getBaseUrl())
                .headers(headers -> {
                    headers.add("accept", "application/json");
                    headers.add("apikey", sdkSettings.getApiKey());
                })
                .httpResponseDecoder(spec -> {
                    spec.h2cMaxContentLength(sdkSettings.getMaxResponseBodySizeInKb() * 1024);
                    return spec;
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
