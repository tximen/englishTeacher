package com.txi.olloama.teacher.vocabulary.service;

import com.txi.olloama.teacher.vocabulary.dto.EnglishVocabularyDTO;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.File;

@Service
public class SourceReaderService {

    private static final File SOURCE_DIR  = new File("G:/intellij/BootLearner/src/main/resources/english");
    private static final File TARGET_DIR  = new File("E:/bastel/english");
    private static final Logger LOGGER = LoggerFactory.getLogger(SourceReaderService.class);

    public boolean doesSourceExist(int index) {
        File file = createSourceFile(index);
        return file.exists() && file.isFile() && file.canRead();
    }

    public EnglishVocabularyDTO readContent(int index) {
        File sourcFile = createSourceFile(index);
        LOGGER.info("read source file: {}", sourcFile.getAbsolutePath());
        return new ObjectMapper()
                 .readValue(sourcFile, EnglishVocabularyDTO.class);


    }


    public void export(int index, EnglishVocabularyDTO item) {
        new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValue(createTargetFile(index), item);
    }


    private static @NonNull File createTargetFile(int index) {
        return new File(SOURCE_DIR, "vocab%04d.json".formatted(index));
    }

    private static @NonNull File createSourceFile(int index) {
        return new File(SOURCE_DIR, "vocab%04d.json".formatted(index));
    }

}
