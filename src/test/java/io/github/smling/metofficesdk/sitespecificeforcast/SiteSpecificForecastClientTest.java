package io.github.smling.metofficesdk.sitespecificeforcast;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.smling.metofficesdk.core.SdkSettings;
import io.github.smling.metofficesdk.sitespecificeforcast.dto.ApiRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
class SiteSpecificForecastClientTest {

    private static final int MOCK_PORT = 8080;
    private static final String MOCK_BASE_URL = "http://localhost:" + MOCK_PORT;

    private static String apiKey;
    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .ignoreIfMissing()
                .load();
        apiKey = dotenv.get("METOFFICE_API_KEY");
        assertNotNull(apiKey, "API key must be provided in the METOFFICE_API_KEY environment variable");
        wireMockServer = new WireMockServer();
        wireMockServer.start();
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    static Stream<Arguments> provideSuccessRequests() {
        return Stream.of(
                Arguments.of(
                        "Valid Request",
                        apiKey,
                        new ApiRequest()
                                .setLatitude(53.4959)
                                .setLongitude(-2.5173)
                                .setIncludeLocationName(true)
                                .setExcludeParameterMetadata(true)
                )
                // Add more successful case arguments as needed
        );
    }

    static Stream<Arguments> provideFailureRequests() {
        return Stream.of(
                Arguments.of(
                        "Invalid Latitude",
                        apiKey,
                        new ApiRequest()
                                .setLatitude(-100.0)
                                .setLongitude(-2.5173)
                                .setIncludeLocationName(true)
                                .setExcludeParameterMetadata(false),
                        false, // expectIllegalArgument
                        false  // expectNullApiKey
                ),
                Arguments.of(
                        "Invalid Longitude",
                        apiKey,
                        new ApiRequest()
                                .setLatitude(53.4959)
                                .setLongitude(-200.0)
                                .setIncludeLocationName(true)
                                .setExcludeParameterMetadata(true),
                        false,
                        false
                ),
                Arguments.of(
                        "Null API Key",
                        null,
                        new ApiRequest()
                                .setLatitude(53.4959)
                                .setLongitude(-2.5173)
                                .setIncludeLocationName(false)
                                .setExcludeParameterMetadata(true),
                        true,  // expectIllegalArgument
                        true   // expectNullApiKey
                ),
                Arguments.of(
                        "Invalid API Key",
                        "abcd1234",
                        new ApiRequest()
                                .setLatitude(53.4959)
                                .setLongitude(-2.5173)
                                .setIncludeLocationName(false)
                                .setExcludeParameterMetadata(true),
                        false,
                        false
                ),
                Arguments.of(
                        "Edge: Min Lat/Lon",
                        apiKey,
                        new ApiRequest()
                                .setLatitude(-90.0)
                                .setLongitude(-180.0)
                                .setIncludeLocationName(true)
                                .setExcludeParameterMetadata(false),
                        false,
                        false
                ),
                Arguments.of(
                        "Edge: Max Lat/Lon",
                        apiKey,
                        new ApiRequest()
                                .setLatitude(90.0)
                                .setLongitude(180.0)
                                .setIncludeLocationName(false)
                                .setExcludeParameterMetadata(true),
                        false,
                        false
                )
        );
    }

    @ParameterizedTest(name = "0}")
    @MethodSource("provideSuccessRequests")
    @Disabled("For test with real endpoint.")
    void getPointDaily_Success(
            String testName,
            String testApiKey,
            ApiRequest apiRequest,
            TestReporter testReporter
    ) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        SdkSettings sdkSettings = new SdkSettings(testApiKey);
        new SiteSpecificForecastClient(sdkSettings)
                .getPointDaily(apiRequest, apiResponse -> {
                    assertNotNull(apiResponse, testName + ": Expected response but got null");
                    testReporter.publishEntry("✅ " + testName + ": " + apiResponse);
                    latch.countDown();
                }, vndError -> {
                    testReporter.publishEntry("❌ " + testName + ": " + vndError);
                    fail("Test failed unexpectedly: " + testName);
                    latch.countDown();
                });

        boolean finished = latch.await(10, TimeUnit.SECONDS);
        assertTrue(finished, "❌ Test timed out");
    }

    @ParameterizedTest(name = "0}")
    @MethodSource("provideFailureRequests")
    @Disabled("For test with real endpoint.")
    void getPointDaily_Failure(
            String testName,
            String testApiKey,
            ApiRequest apiRequest,
            boolean expectIllegalArgument,
            boolean expectNullApiKey,
            TestReporter testReporter
    ) throws InterruptedException {
        if (expectIllegalArgument) {
            assertThrows(IllegalArgumentException.class, () -> {
                SdkSettings sdkSettings = new SdkSettings(testApiKey);
                new SiteSpecificForecastClient(sdkSettings)
                        .getPointDaily(apiRequest, r -> {
                        }, e -> {
                        });
            }, testName + ": Expected IllegalArgumentException");
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);
        SdkSettings sdkSettings = new SdkSettings(testApiKey);
        new SiteSpecificForecastClient(sdkSettings)
                .getPointDaily(apiRequest, apiResponse -> {
                    testReporter.publishEntry("⚠️ " + testName + ": Unexpected success");
                    fail("Test should not have succeeded: " + testName);
                    latch.countDown();
                }, vndError -> {
                    assertNotNull(vndError, testName + ": Expected error but got null");
                    testReporter.publishEntry("✅ " + testName + " failed as expected: " + vndError);
                    latch.countDown();
                });

        boolean finished = latch.await(10, TimeUnit.SECONDS);
        assertTrue(finished, "❌ Test timed out");
    }

    @ParameterizedTest(name = "{0} [MOCK]")
    @MethodSource("provideSuccessRequests")
    void getPointDaily_Success_Mock(
            String testName,
            String testApiKey,
            ApiRequest apiRequest,
            TestReporter testReporter
    ) throws InterruptedException {
        configureFor("localhost", MOCK_PORT);
        stubFor(get(urlPathMatching("/sitespecific/v0/point/daily*")).willReturn(aResponse().withBody("{}")));
        SdkSettings sdkSettings = new SdkSettings(MOCK_BASE_URL, testApiKey, SdkSettings.DEFAULT_MAX_RESPONSE_BODY_SIZE_IN_KB);
        CountDownLatch latch = new CountDownLatch(1);
        new SiteSpecificForecastClient(sdkSettings)
                .getPointDaily(apiRequest, apiResponse -> {
                    assertNotNull(apiResponse, testName + ": Expected response but got null");
                    testReporter.publishEntry("✅ " + testName + ": " + apiResponse);
                    latch.countDown();
                }, vndError -> {
                    testReporter.publishEntry("❌ " + testName + ": " + vndError);
                    fail("Test failed unexpectedly: " + testName);
                    latch.countDown();
                });

        boolean finished = latch.await(10, TimeUnit.SECONDS);
        assertTrue(finished, "❌ Test timed out");
    }

    @ParameterizedTest(name = "{0} [MOCK]")
    @MethodSource("provideFailureRequests")
    void getPointDaily_Failure_Mock(
            String testName,
            String testApiKey,
            ApiRequest apiRequest,
            boolean expectIllegalArgument,
            boolean expectNullApiKey,
            TestReporter testReporter
    ) throws InterruptedException {
        configureFor("localhost", MOCK_PORT);
        stubFor(get(urlPathMatching("/sitespecific/v0/point/daily*")).willReturn(aResponse().withBody("{}")));
        SdkSettings sdkSettings = new SdkSettings(MOCK_BASE_URL, testApiKey, SdkSettings.DEFAULT_MAX_RESPONSE_BODY_SIZE_IN_KB);
        if (expectIllegalArgument) {
            assertThrows(IllegalArgumentException.class, () -> {
                new SiteSpecificForecastClient(sdkSettings)
                        .getPointDaily(apiRequest, r -> {
                        }, e -> {
                        });
            }, testName + ": Expected IllegalArgumentException");
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);
        new SiteSpecificForecastClient(sdkSettings)
                .getPointDaily(apiRequest, apiResponse -> {
                    testReporter.publishEntry("⚠️ " + testName + ": Unexpected success");
                    fail("Test should not have succeeded: " + testName);
                    latch.countDown();
                }, vndError -> {
                    assertNotNull(vndError, testName + ": Expected error but got null");
                    testReporter.publishEntry("✅ " + testName + " failed as expected: " + vndError);
                    latch.countDown();
                });

        boolean finished = latch.await(10, TimeUnit.SECONDS);
        assertTrue(finished, "❌ Test timed out");
    }
}