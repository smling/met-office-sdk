package io.github.smling.metofficesdk.sitespecificeforcast.dto;

import java.util.List;

public record Geometry(
        String type,
        List<Double> coordinates
) {}