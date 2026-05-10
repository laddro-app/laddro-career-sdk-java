package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateDetail extends Template {
    @JsonProperty("availableColors") public List<Map<String, String>> availableColors;
    @JsonProperty("availableFonts") public List<Map<String, String>> availableFonts;
    public Map<String, Object> defaults;
}
