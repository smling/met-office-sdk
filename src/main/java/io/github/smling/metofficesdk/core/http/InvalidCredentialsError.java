package io.github.smling.metofficesdk.core.http;

import java.util.Objects;

public class InvalidCredentialsError extends ApiError {
    private String code;
    private String message;
    private String description;

    public String getCode() {
        return code;
    }

    public InvalidCredentialsError setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public InvalidCredentialsError setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InvalidCredentialsError setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InvalidCredentialsError that = (InvalidCredentialsError) o;
        return Objects.equals(code, that.code) && Objects.equals(message, that.message) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, description);
    }

    @Override
    public String toString() {
        return "InvalidCredentialsError{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
