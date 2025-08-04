package io.github.smling.metofficesdk.sitespecificeforcast.dto;

public record Feature(
        String type,
        Geometry geometry,
        Properties properties
) {}