package com.txi.olloama.teacher.vocabulary.service;


import com.txi.olloama.teacher.vocabulary.model.VocabularyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class VocabularyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VocabularyService.class);

    private final ChatClient chatClient;
    private final BeanOutputConverter<VocabularyResponse> converter;

    public VocabularyService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
        this.converter = new BeanOutputConverter<>(VocabularyResponse.class);
    }

    public Optional<VocabularyResponse> lookup(String englishWord) {
        try {
            String raw = chatClient.prompt()
                    .user(englishWord)
                    .options(promptOptions())
                    .call()
                    .content();
            return Optional.of(converter.convert(raw));
        } catch (Exception exception) {
            LOGGER.error("Error while trying to lookup vocabulary", exception);
            return Optional.empty();
        }
    }


    private OllamaChatOptions.Builder promptOptions( ) {
       return OllamaChatOptions.builder()
                .format("json")
                .outputSchema(converter.getJsonSchema())
                .temperature(0.2);
    }

}