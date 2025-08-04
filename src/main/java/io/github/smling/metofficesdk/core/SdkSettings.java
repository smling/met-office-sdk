package io.github.smling.metofficesdk.core;

public class SdkSettings {
    public static final String DEFAULT_BASE_URL = "https://data.hub.api.metoffice.gov.uk";
    public static final int DEFAULT_MAX_RESPONSE_BODY_SIZE_IN_KB = 512;

    private final String baseUrl;
    private final String apiKey;
    private final int maxResponseBodySizeInKb;

    public SdkSettings(String apiKey) {
        this(DEFAULT_BASE_URL, apiKey, DEFAULT_MAX_RESPONSE_BODY_SIZE_IN_KB);
    }

    public SdkSettings(String apiKey, int maxResponseBodySizeInKb) {
        this(DEFAULT_BASE_URL, apiKey, maxResponseBodySizeInKb);
    }

    public SdkSettings(String baseUrl, String apiKey, int maxResponseBodySizeInKb) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.maxResponseBodySizeInKb = maxResponseBodySizeInKb;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public int getMaxResponseBodySizeInKb() {
        return maxResponseBodySizeInKb;
    }
}
