/**
 * Represents a unit of measurement, including label and its symbol.
 */
package io.github.smling.metofficesdk.sitespecificeforcast.dto;

public record Unit(
        String label,
        Symbol symbol
) {}
