package io.github.smling.metofficesdk.core.http;

public record HttpResult(
        int statusCode,
        String body
) {}
