package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageList {
    public List<Map<String, String>> languages;
}
