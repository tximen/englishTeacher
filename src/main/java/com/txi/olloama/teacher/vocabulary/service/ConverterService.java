package com.txi.olloama.teacher.vocabulary.service;

import com.txi.olloama.teacher.vocabulary.dto.EnglishVocabularyDTO;
import com.txi.olloama.teacher.vocabulary.dto.SentenceDTO;
import com.txi.olloama.teacher.vocabulary.model.SentenceExample;
import com.txi.olloama.teacher.vocabulary.model.VocabularyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class ConverterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterService.class);

    public List<SentenceDTO> englishSentences(EnglishVocabularyDTO sourceItem, VocabularyResponse vocab) {
        List<SentenceDTO> sentences = new ArrayList<>(sourceItem.sentences());
        for (SentenceExample sample : vocab.sentences()) {
            sentences.add(convertEnglish(sample));
        }
        return sentences;
    }

    public List<SentenceDTO> germanSentences(EnglishVocabularyDTO sourceItem, VocabularyResponse vocab) {
        List<SentenceDTO> sentences = new ArrayList<>(sourceItem.saetze());
        for (SentenceExample sample : vocab.sentences()) {
            sentences.add(convertGerman(sample));
        }
        return sentences;
    }

    public SentenceDTO convertEnglish(SentenceExample source) {
        return convertMessage(source.sentence());
    }

    public SentenceDTO convertGerman(SentenceExample source) {

        return convertMessage(source.translation());
    }


    private SentenceDTO convertMessage(String sentence) {
        int index = sentence.indexOf("**");
        if (index == -1) {
            return new SentenceDTO(sentence, "", "");
        } else {
            String presentence = sentence.substring(0, index);
            String nextPart = sentence.substring(index + 2);
            index = nextPart.indexOf("**");
            if (index == -1) {
                throw new ConverterException("Invalid sentence: " + nextPart);
            }
            return new SentenceDTO(presentence.trim(),
                                  nextPart.substring(0, index),
                                  nextPart.substring(index + 2).trim());
        }
    }


    public boolean doesMatch(String source, String target) {
        Set<String> sourceSet = Stream.of(source.split("\\,")).map(String::trim).collect(Collectors.toSet());
        for (String word : target.split("\\,")) {
            String contest = word.trim();
            LOGGER.info("match [{}], [{}] --> {}",  contest, source, sourceSet.contains(contest));
            if (sourceSet.contains(contest)) {
                return true;
            }
        }
        return false;
    }

    public String merge(String source, String target) {
        Set<String> values = new HashSet<>(Stream.of(source.split("\\,")).map(String::trim).collect(Collectors.toSet()));
        values.addAll(Stream.of(target.split("\\,")).map(String::trim).collect(Collectors.toSet()));
        return values.stream().sorted().collect(Collectors.joining(", "));
    }
}
