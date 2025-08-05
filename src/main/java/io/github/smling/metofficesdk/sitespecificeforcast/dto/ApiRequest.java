package io.github.smling.metofficesdk.sitespecificeforcast.dto;

import java.util.Objects;

public class ApiRequest {
    private String dataSource;
    private Boolean excludeParameterMetadata;
    private Boolean includeLocationName;
    private Double latitude;
    private Double longitude;

    public String getDataSource() {
        return dataSource;
    }

    public ApiRequest setDataSource(String dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public Boolean getExcludeParameterMetadata() {
        return excludeParameterMetadata;
    }

    public ApiRequest setExcludeParameterMetadata(Boolean excludeParameterMetadata) {
        this.excludeParameterMetadata = excludeParameterMetadata;
        return this;
    }

    public Boolean getIncludeLocationName() {
        return includeLocationName;
    }

    public ApiRequest setIncludeLocationName(Boolean includeLocationName) {
        this.includeLocationName = includeLocationName;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public ApiRequest setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public ApiRequest setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public String toString() {
        return "ApiRequest{" +
                "dataSource='" + dataSource + '\'' +
                ", excludeParameterMetadata=" + excludeParameterMetadata +
                ", includeLocationName=" + includeLocationName +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ApiRequest that = (ApiRequest) o;
        return Objects.equals(dataSource, that.dataSource) && Objects.equals(excludeParameterMetadata, that.excludeParameterMetadata) && Objects.equals(includeLocationName, that.includeLocationName) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSource, excludeParameterMetadata, includeLocationName, latitude, longitude);
    }
}
