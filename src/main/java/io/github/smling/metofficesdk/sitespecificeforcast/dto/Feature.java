/**
 * Represents a geographical feature in the API response, including its type, geometry, and properties.
 */
package io.github.smling.metofficesdk.sitespecificeforcast.dto;

public record Feature(
        String type,
        Geometry geometry,
        Properties properties
) {}