package io.github.smling.metofficesdk.sitespecificeforcast.dto;

import java.util.List;

public record Properties(
        Location location,
        double requestPointDistance,
        String modelRunDate,
        List<TimeSeriesEntry> timeSeries
) {}