package com.txi.olloama.teacher;

import com.txi.olloama.teacher.vocabulary.dto.EnglishVocabularyDTO;
import com.txi.olloama.teacher.vocabulary.dto.SentenceDTO;
import com.txi.olloama.teacher.vocabulary.model.SentenceExample;
import com.txi.olloama.teacher.vocabulary.model.Synonym;
import com.txi.olloama.teacher.vocabulary.model.VocabularyResponse;
import com.txi.olloama.teacher.vocabulary.service.ConverterException;
import com.txi.olloama.teacher.vocabulary.service.ConverterService;
import com.txi.olloama.teacher.vocabulary.service.SourceReaderService;
import com.txi.olloama.teacher.vocabulary.service.VocabularyService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EnglishLesson implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnglishLesson.class);

    private final String source;
    private final SourceReaderService sourceReaderService;
    private final ConverterService converterService;
    private final VocabularyService vocabularyService;

    public EnglishLesson(SourceReaderService sourceReaderService, ConverterService converterService, VocabularyService vocabularyService) {
        this.sourceReaderService = sourceReaderService;
        this.converterService = converterService;
        this.vocabularyService = vocabularyService;

        this.source = "Ollama %s".formatted(new SimpleDateFormat("dd.MM.yyy").format(new Date()));
    }


    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Starting English Lesson");
        for (int i=38; i<39; i++) {
            processVocabByIndex(i);
            if (i%3==0) {
                System.out.println("cooling time. index: " + i);
                Thread.sleep(20_000);
            }
            if (i%7==0) {
                System.out.println("extended cooling time index: " + i);
                Thread.sleep(60_000);
            }

        }


// Access the thinking process


    }

    private void processVocabByIndex(int index) {
        if (this.sourceReaderService.doesSourceExist(index)) {
            EnglishVocabularyDTO sourceItem =  this.sourceReaderService.readContent(index);
            LOGGER.info("{}: {}", index, sourceItem.vocabulary());
            System.out.println(sourceItem.vocabulary());
            Optional<VocabularyResponse> vocab = this.vocabularyService.lookup(sourceItem.vocabulary());
            System.out.println("******** VERGLEICH");
            if (vocab.isPresent() && this.converterService.doesMatch(sourceItem.german(), vocab.get().wordTranslation())) {

                this.sourceReaderService.export(index, update(sourceItem,  vocab.get()));
            }
        }
    }

    private EnglishVocabularyDTO update(EnglishVocabularyDTO sourceItem, VocabularyResponse vocab) {
           return new EnglishVocabularyDTO(this.source,
                    sourceItem.vocabulary(),
                    sentences(sourceItem, vocab),
                    saetze(sourceItem, vocab),
                    meaning(sourceItem, vocab),
                    german(sourceItem, vocab),
                    synonym(sourceItem, vocab));
    }

    private static String meaning(EnglishVocabularyDTO sourceItem, VocabularyResponse vocab) {
        if (StringUtils.isBlank(vocab.wordDescription()) || sourceItem.description().contains("mean")) {
            return sourceItem.description();
        }  else {
           return vocab.wordDescription();
        }
    }


    private List<SentenceDTO> saetze(EnglishVocabularyDTO sourceItem, VocabularyResponse vocab) {
        return this.converterService.germanSentences(sourceItem, vocab);
    }

    private List<SentenceDTO> sentences(EnglishVocabularyDTO sourceItem, VocabularyResponse vocab) {
        return this.converterService.englishSentences(sourceItem, vocab);
    }

    private String german(EnglishVocabularyDTO sourceItem, VocabularyResponse vocab) {
        return this.converterService.merge(sourceItem.german(), vocab.wordTranslation());
    }

    private String synonym(EnglishVocabularyDTO sourceItem, VocabularyResponse vocab) {
        return this.converterService.merge(sourceItem.synonyms(),
                vocab.synonyms().stream().map(Synonym::word).collect(Collectors.joining(","))
        );
    }


    /**
    public String getExamples(String word) {

        return this.chatClient.prompt()
                .user(word)
                .options(OllamaChatOptions.builder().format("json"))
                .call()
                .content();
    }
     */
}
