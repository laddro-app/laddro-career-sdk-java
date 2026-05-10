package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Template {
    public String id;
    public String name;
    @JsonProperty("atsScore") public int atsScore;
    @JsonProperty("layoutType") public String layoutType;
    @JsonProperty("supportsProfileImage") public boolean supportsProfileImage;
}
