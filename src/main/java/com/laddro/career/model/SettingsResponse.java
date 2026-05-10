package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SettingsResponse {
    public Map<String, Object> ai;
}
