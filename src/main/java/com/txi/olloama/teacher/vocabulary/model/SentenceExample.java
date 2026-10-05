package com.txi.olloama.teacher.vocabulary.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.List;


public record SentenceExample(

        @JsonProperty(required = true)
        @JsonPropertyDescription("Difficulty level, e.g. 'Simple (A1/A2)', 'Intermediate (B1/B2)', 'Advanced (C1/C2)'")
        String level,

        @JsonProperty(required = true)
        @JsonPropertyDescription("English sentence with the target word wrapped in **double asterisks**")
        String sentence,

        @JsonProperty(required = true)
        @JsonPropertyDescription("German translation of the sentence")
        String translation,

        @JsonProperty(required = true)
        @JsonPropertyDescription("Short German explanation of the target word's nuance in this sentence")
        String explanation,

        @JsonProperty(required = true)
        @JsonPropertyDescription("Word-by-word German glossary of the English sentence")
        List<WordPair> wordByWord
) {}