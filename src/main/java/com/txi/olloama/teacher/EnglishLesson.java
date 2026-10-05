package com.txi.olloama.teacher;

import com.txi.olloama.teacher.vocabulary.model.SentenceExample;
import com.txi.olloama.teacher.vocabulary.model.VocabularyResponse;
import com.txi.olloama.teacher.vocabulary.service.ConverterService;
import com.txi.olloama.teacher.vocabulary.service.SourceReaderService;
import com.txi.olloama.teacher.vocabulary.service.VocabularyService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EnglishLession implements CommandLineRunner {

    private final SourceReaderService sourceReaderService;
    private final ConverterService converterService;
    private final VocabularyService vocabularyService;

    public EnglishLession(ConverterService converterService, VocabularyService vocabularyService) {
        this.sourceReaderService = new SourceReaderService();
        this.converterService = converterService;
        this.vocabularyService = vocabularyService;
    }


    @Override
    public void run(String... args) throws Exception {


// Access the thinking process

System.out.println("START ***************");
        VocabularyResponse vocab = this.vocabularyService.lookup("embroidery");

        for (SentenceExample example : vocab.sentences()) {
           // System.out.println(this.converterService.convertEnglish(example));

            System.out.println(vocab.wordTranslation());
            System.out.println(example.sentence());

            System.out.println(example.translation());
            System.out.println(example.wordByWord());
        }
        System.out.println("END ***************");

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
