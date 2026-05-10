package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateAISettingsRequest {
    public String provider;
    public String model;
    @JsonProperty("apiKey") public String apiKey;

    public UpdateAISettingsRequest(String provider, String apiKey) {
        this.provider = provider;
        this.apiKey = apiKey;
    }
}
