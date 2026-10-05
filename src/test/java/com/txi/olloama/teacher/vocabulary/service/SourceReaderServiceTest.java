package com.txi.olloama.teacher.vocabulary.service;

import com.txi.olloama.teacher.vocabulary.dto.EnglishVocabularyDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SourceReaderServiceTest {

    private SourceReaderService service = new SourceReaderService();

    @Test
    public void testExist() {
        Assertions.assertThat(service.doesSourceExist(1)).isTrue();
    }

    @Test
    public void testReadContent() {
        EnglishVocabularyDTO item = service.readContent(1);
        System.out.println(item.vocabulary());
        System.out.println(item.german());
    }
}
