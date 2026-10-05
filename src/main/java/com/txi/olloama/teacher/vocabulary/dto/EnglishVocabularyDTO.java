package com.txi.olloama.teacher.vocabulary.dto;

import java.util.List;

public record EnglishVocabularyDTO(String source, String vocabulary, List<SentenceDTO> sentences, List<SentenceDTO> saetze,
                                   String description, String german, String synonyms) {
}
