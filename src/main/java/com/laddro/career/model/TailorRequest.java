package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TailorRequest {
    @JsonProperty("resumeId") public String resumeId;
    @JsonProperty("positionName") public String positionName;
    @JsonProperty("jobDescription") public String jobDescription;
    @JsonProperty("jobUrl") public String jobUrl;
    public String mode;
    public String language;
    @JsonProperty("includeCoverLetter") public Boolean includeCoverLetter;
    @JsonProperty("templateId") public String templateId;
    @JsonProperty("colorId") public String colorId;
    public String font;

    public TailorRequest(String positionName) {
        this.positionName = positionName;
    }
}
