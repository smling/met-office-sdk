package io.github.smling.metofficesdk.sitespecificeforcast.dto;


import io.github.smling.metofficesdk.core.http.ApiError;

import java.util.Objects;

public class VndError extends ApiError {
    private String logref;
    private String message;

    public VndError() {
    }

    public VndError(String logref, String message) {
        this.logref = logref;
        this.message = message;
    }

    public String getLogref() {
        return logref;
    }

    public VndError setLogref(String logref) {
        this.logref = logref;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public VndError setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VndError vndError = (VndError) o;
        return Objects.equals(logref, vndError.logref) && Objects.equals(message, vndError.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logref, message);
    }

    @Override
    public String toString() {
        return "VndError{" +
                "logref='" + logref + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}