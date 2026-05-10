package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCoverLetterResponse {
    @JsonProperty("coverLetterId") public String coverLetterId;
    public String title;
    public String status;
}
