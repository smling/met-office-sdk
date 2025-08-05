/**
 * Represents a forecast parameter, including its type, description, and measurement unit.
 */
package io.github.smling.metofficesdk.sitespecificeforcast.dto;

public record Parameter(
        String type,
        String description,
        Unit unit
) {}
