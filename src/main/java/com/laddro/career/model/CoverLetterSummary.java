package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoverLetterSummary {
    public String id;
    @JsonProperty("coverLetterId") public String coverLetterId;
    public String title;
    public String letterContent;
    public Map<String, Object> data;
    @JsonProperty("createdAt") public String createdAt;
    @JsonProperty("updatedAt") public String updatedAt;
}
