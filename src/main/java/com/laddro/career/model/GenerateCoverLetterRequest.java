package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenerateCoverLetterRequest {
    @JsonProperty("resumeId") public String resumeId;
    @JsonProperty("positionName") public String positionName;
    @JsonProperty("jobDescription") public String jobDescription;
    @JsonProperty("jobUrl") public String jobUrl;
    public String language;
    @JsonProperty("templateId") public String templateId;

    public GenerateCoverLetterRequest(String positionName) {
        this.positionName = positionName;
    }
}
