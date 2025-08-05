package io.github.smling.metofficesdk.sitespecificeforcast;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.smling.metofficesdk.core.BaseClient;
import io.github.smling.metofficesdk.core.SdkSettings;
import io.github.smling.metofficesdk.core.http.ApiError;
import io.github.smling.metofficesdk.core.http.HttpResult;
import io.github.smling.metofficesdk.core.http.InvalidCredentialsError;
import io.github.smling.metofficesdk.sitespecificeforcast.dto.ApiRequest;
import io.github.smling.metofficesdk.sitespecificeforcast.dto.ApiResponse;
import io.github.smling.metofficesdk.sitespecificeforcast.dto.VndError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class SiteSpecificForecastClient extends BaseClient {
    private final SdkSettings sdkSettings;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(SiteSpecificForecastClient.class);

    public SiteSpecificForecastClient(SdkSettings sdkSettings) {
        this.sdkSettings = sdkSettings;
    }

    public void getPointDaily(ApiRequest apiRequest,
                              Consumer<ApiResponse> onSuccess,
                              Consumer<ApiError> onError
    ) {
        String path = "/sitespecific/v0/point/daily";

        Map<String, String> queryParams = createQueryParameterMap(apiRequest);
        String query = buildQuery(queryParams);
        logger.debug("Constructed URL: {}{}{}",sdkSettings.getBaseUrl(), path, query);
        getHttpClient(sdkSettings).get()
                .uri(path + query)
                .responseSingle((res, bytes) -> bytes
                        .asString()
                        .map(body -> new HttpResult(res.status().code(), body))
                )
                .subscribe(result -> {
                    logger.debug("Received HTTP {} response, body: {}", result.statusCode(), result.body());
                    try {
                        if (result.statusCode() >= 200 && result.statusCode() < 300) {
                            ApiResponse response = objectMapper.readValue(result.body(), ApiResponse.class);
                            logger.debug("Parsed successful response: {}", response);
                            onSuccess.accept(response);
                        } else {
                            ApiError error;
                            if(result.statusCode() == 401) {
                                error = objectMapper.readValue(result.body(), InvalidCredentialsError.class);
                            } else {
                                error = objectMapper.readValue(result.body(), VndError.class);
                            }
                            logger.warn("Parsed error response: {}", error);
                            onError.accept(error);
                        }
                    } catch (Exception e) {
                        logger.error("JSON parsing failed", e);
                        onError.accept(new VndError("JSON_PARSE_ERROR", "Failed to parse response: " + e.getMessage()));
                    }
                }, throwable -> {
                    logger.error("HTTP client exception", throwable);
                    onError.accept(new VndError("CLIENT_EXCEPTION", throwable.getMessage()));
                });
    }

    protected Map<String, String> createQueryParameterMap(ApiRequest apiRequest) {
        Map<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put("latitude", apiRequest.getLatitude().toString());
        queryParams.put("longitude", apiRequest.getLongitude().toString());
        if(Objects.nonNull(apiRequest.getExcludeParameterMetadata())) {
            queryParams.put("excludeParameterMetadata", apiRequest.getExcludeParameterMetadata().toString());
        }
        if(Objects.nonNull(apiRequest.getIncludeLocationName())) {
            queryParams.put("includeLocationName", apiRequest.getIncludeLocationName().toString());
        }
        if(Objects.nonNull(apiRequest.getDataSource())) {
            queryParams.put("dataSource", apiRequest.getDataSource());
        }
        return queryParams;
    }
}
