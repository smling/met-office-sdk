package io.github.smling.metofficesdk.sitespecificeforcast.dto;

public record Parameter(
        String type,
        String description,
        Unit unit
) {}