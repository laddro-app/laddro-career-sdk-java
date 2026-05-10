package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCoverLetterRequest {
    public String title;
    @JsonProperty("fullName") public String fullName;
    @JsonProperty("jobTitle") public String jobTitle;
    public String address;
    public String email;
    public String phone;
    @JsonProperty("companyName") public String companyName;
    @JsonProperty("hiringManager") public String hiringManager;
    @JsonProperty("letterContent") public String letterContent;

    public CreateCoverLetterRequest(String fullName, String letterContent) {
        this.fullName = fullName;
        this.letterContent = letterContent;
    }
}
