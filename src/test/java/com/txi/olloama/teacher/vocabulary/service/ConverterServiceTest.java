package com.txi.olloama.teacher.vocabulary.service;

import com.txi.olloama.teacher.vocabulary.model.SentenceExample;
import com.txi.olloama.teacher.vocabulary.model.WordPair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ConverterServiceTest {

    private ConverterService service = new ConverterService();

    @Test
    public void testEnglishExample() {
        var x = service.convertEnglish(createExample("he waiter’s **courtesy** – bringing us extra napkins and water – made the dining experience much more pleasant."));
        System.out.println(x);

    }

    @Test
    public void testDeutschExample() {
        var english = "He sought to experience the artist’s creative process **vicariously**, poring over his journals and studying his techniques, yet always remained an outsider to his deeply felt inspiration.";
        var deutsch = "";
        var x = service.convertEnglish(createExample("he waiter’s **courtesy** – bringing us extra napkins and water – made the dining experience much more pleasant."));
        System.out.println(x);

    }


    private SentenceExample createExample(String sentence) {
       return new SentenceExample("B1/B2", sentence, "", "", new ArrayList<>());
    }

    private SentenceExample createExample(String sentence, String german, List<WordPair> wordByWord) {
        return new SentenceExample("B1/B2", sentence, german, "", wordByWord);
    }

    @Test
    public void testDoesMatch() {
        service.doesMatch("verabscheuungswürdig, verachtenswert, jämmerlich, verachtenswürdig, abscheulich", "abscheulich, verwerflich, widerwillig, verabscheuend, widerwärtig, unangenehm");

        System.out.println(service.merge("verabscheuungswürdig, verachtenswert, jämmerlich, verachtenswürdig, abscheulich", "abscheulich, verwerflich, widerwillig, verabscheuend, widerwärtig, unangenehm"));
    }
}
