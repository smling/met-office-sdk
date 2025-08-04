package io.github.smling.metofficesdk.sitespecificeforcast.dto;

import java.util.List;
import java.util.Map;

public record ApiResponse(
        String type,
        List<Feature> features,
        List<Map<String, Parameter>> parameters
) {}
