package com.txi.olloama.teacher.vocabulary.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record WordPair(

        @JsonProperty(required = true)
        @JsonPropertyDescription("Single English word")
        String en,

        @JsonProperty(required = true)
        @JsonPropertyDescription("German meaning of the word in this context")
        String de
) {

}