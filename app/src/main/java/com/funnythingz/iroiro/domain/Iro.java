package com.funnythingz.iroiro.domain;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonProperty;

@Generated("org.jsonschema2pojo")

public class Iro {
    @JsonProperty("id")
    public int id;

    @JsonProperty("color")
    public Color color;

    @JsonProperty("content")
    public String content;
}
