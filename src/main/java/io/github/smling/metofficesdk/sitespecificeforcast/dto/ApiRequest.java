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

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Boolean getExcludeParameterMetadata() {
        return excludeParameterMetadata;
    }

    public void setExcludeParameterMetadata(Boolean excludeParameterMetadata) {
        this.excludeParameterMetadata = excludeParameterMetadata;
    }

    public Boolean getIncludeLocationName() {
        return includeLocationName;
    }

    public void setIncludeLocationName(Boolean includeLocationName) {
        this.includeLocationName = includeLocationName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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
