/**
 * Configuration class for SDK settings including base URL, API key, and maximum response body size.
 */
package io.github.smling.metofficesdk.core;

public class SdkSettings {
    /**
     * Default base URL for Met Office DataHub API.
     */
    public static final String DEFAULT_BASE_URL = "https://data.hub.api.metoffice.gov.uk";
    /**
     * Default max response body size, in KB.
     */
    public static final int DEFAULT_MAX_RESPONSE_BODY_SIZE_IN_KB = 512;

    private final String baseUrl;
    private final String apiKey;
    private final int maxResponseBodySizeInKb;

    /**
     * Constructor with API key. Uses default base URL and response size.
     * @param apiKey The API key to use.
     */
    public SdkSettings(String apiKey) {
        this(DEFAULT_BASE_URL, apiKey, DEFAULT_MAX_RESPONSE_BODY_SIZE_IN_KB);
    }

    /**
     * Constructor with API key and max response body size. Uses default base URL.
     * @param apiKey The API key to use.
     * @param maxResponseBodySizeInKb Max response body size in KB.
     */
    public SdkSettings(String apiKey, int maxResponseBodySizeInKb) {
        this(DEFAULT_BASE_URL, apiKey, maxResponseBodySizeInKb);
    }

    /**
     * Full constructor specifying all settings.
     * @param baseUrl The base URL for API requests.
     * @param apiKey The API key to use.
     * @param maxResponseBodySizeInKb Max response body size, in KB.
     */
    public SdkSettings(String baseUrl, String apiKey, int maxResponseBodySizeInKb) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.maxResponseBodySizeInKb = maxResponseBodySizeInKb;
    }

    /**
     * @return The API base URL.
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * @return The API key.
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @return The maximum allowed response body size (in KB).
     */
    public int getMaxResponseBodySizeInKb() {
        return maxResponseBodySizeInKb;
    }
}
