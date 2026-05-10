package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResumeSummary {
    public String id;
    @JsonProperty("resumeId") public String resumeId;
    public String title;
    @JsonProperty("isDefault") public boolean isDefault;
    @JsonProperty("createdAt") public String createdAt;
    @JsonProperty("updatedAt") public String updatedAt;
}
