package com.txi.olloama.teacher.vocabulary.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.List;

public record VocabularyResponse(

        @JsonProperty(required = true)
        @JsonPropertyDescription("The English vocabulary word in its base form")
        String word,

        @JsonProperty(required = true)
        @JsonPropertyDescription("German translation of the word, all common meanings separated by commas")
        String wordTranslation,

        @JsonProperty("word_description")
        String wordDescription,

        @JsonProperty(required = true)
        @JsonPropertyDescription("List of 4 to 6 English synonyms with German translation and nuance explanation")
        List<Synonym> synonyms,

        @JsonProperty(required = true)
        @JsonPropertyDescription("Exactly 4 example sentences with progressive difficulty")
        List<SentenceExample> sentences
) {}