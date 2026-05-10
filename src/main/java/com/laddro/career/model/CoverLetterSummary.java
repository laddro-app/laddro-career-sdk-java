package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoverLetterSummary {
    public String id;
    @JsonProperty("coverLetterId") public String coverLetterId;
    public String title;
    @JsonProperty("createdAt") public String createdAt;
    @JsonProperty("updatedAt") public String updatedAt;
}
