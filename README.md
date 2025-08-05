# Met Office SDK

This project is a Java SDK for interacting with the UK Met Office DataHub's Site Specific Forecasts API. It provides convenient client-side tools to fetch weather forecast data (e.g., daily, hourly, and three-hourly forecasts) for specified locations from the Met Office, handling response parsing and error handling for consumers.

## Description

The SDK implements domain objects and clients that allow Java applications to easily integrate with the Site Specific Forecasts API from the UK Met Office (see [official documentation](https://datahub.metoffice.gov.uk/docs/f/category/site-specific/overview)). The library supplies:

- Type-safe models for API requests and responses (e.g., `ApiRequest`, `ApiResponse`, `Feature`, `Parameter`)
- A client (`SiteSpecificForecastClient`) to fetch forecasts for specific sites and time ranges (daily, hourly, three-hourly)
- Structured error handling classes (`ApiError`, `InvalidCredentialsError`)
- Configuration via `SdkSettings` for API keys, base URLs, and response size limits

## How to Use

### Adding the SDK to Your Project

Include the SDK source (or compiled JAR) in your project's classpath. Ensure all dependencies are available.

### Example Usage

```java
import io.github.smling.metofficesdk.core.SdkSettings;
import io.github.smling.metofficesdk.sitespecificeforcast.SiteSpecificForecastClient;
import io.github.smling.metofficesdk.sitespecificeforcast.dto.ApiRequest;
import io.github.smling.metofficesdk.sitespecificeforcast.dto.ApiResponse;
import io.github.smling.metofficesdk.core.http.ApiError;

import java.util.function.Consumer;

public class Example {
    public static void main(String[] args) {
        // Set your Met Office API key here
        String apiKey = "YOUR_API_KEY";

        // Initialize settings and client
        SdkSettings settings = new SdkSettings(apiKey);
SiteSpecificForecastClient client = new SiteSpecificForecastClient(settings);

        // Build a simple ApiRequest (replace with real lat/lon/siteId/parameters)
        ApiRequest request = new ApiRequest()
                .setLatitude(50.4959)
                .setLongitude(-2.5173)
                ;

        // Success and error callbacks
Consumer<ApiResponse> onSuccess = response -> {
            System.out.println("Forecast Type: " + response.type());
            for (io.github.smling.metofficesdk.sitespecificeforcast.dto.Feature feat : response.features()) {
                System.out.println("Feature: " + feat.type());
            }
            // Access parameters if needed
            if (response.parameters() != null) {
                for (Map<String, io.github.smling.metofficesdk.sitespecificeforcast.dto.Parameter> paramMap : response.parameters()) {
                    System.out.println("Parameters: " + paramMap.keySet());
                }
            }
};

Consumer<ApiError> onError = error -> {
            System.err.println("Error occurred: " + error);
};

// Fetch a daily point forecast
client.getPointDaily(request, onSuccess, onError);
    }
}
```

Other methods are available for three-hourly and hourly requests (`getPointThreeHourly`, `getPointHourly`).

## Dependencies

| Dependency | Purpose                            | Version/Notes  |
|------------|------------------------------------|----------------|
| Java       | Language/runtime                   | 17 or later    |
| Jackson    | JSON serialization/deserialization |                |
| slf4j      | Logging framework                  |                |
| WireMock   | API mocking for tests              | Test, optional |
| JUnit 5    | Unit testing framework             | Test, optional |
## Documentation

- Met Office DataHub Site Specific Forecasts API: [Official Documentation](https://datahub.metoffice.gov.uk/docs/f/category/site-specific/overview)
- SDK JavaDocs: Generate using `javadoc` or your preferred IDE tools

## License

See LICENSE file for details.
