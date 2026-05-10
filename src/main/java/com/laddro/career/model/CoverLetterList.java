package com.laddro.career.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoverLetterList {
    public List<CoverLetterSummary> items;
    public int total;
    public int limit;
    public int offset;
}
