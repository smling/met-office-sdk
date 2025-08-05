/**
 * Represents a response from the Site Specific Forecast API.
 * Contains the type of response, a list of features (forecast data), and a list of parameter maps.
 */
package io.github.smling.metofficesdk.sitespecificeforcast.dto;

import java.util.List;
import java.util.Map;

public record ApiResponse(
        String type,
        List<Feature> features,
        List<Map<String, Parameter>> parameters
) {}
