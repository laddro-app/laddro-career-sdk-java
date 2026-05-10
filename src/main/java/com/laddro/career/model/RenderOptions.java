package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RenderOptions {
    @JsonProperty("templateId") public String templateId;
    public String locale;
    @JsonProperty("colorId") public String colorId;
    public String font;
    public Double spacing;
    public Double margin;
    @JsonProperty("fontSize") public Double fontSize;
    @JsonProperty("pageNumbering") public String pageNumbering;

    public RenderOptions(String templateId) {
        this.templateId = templateId;
    }
}
