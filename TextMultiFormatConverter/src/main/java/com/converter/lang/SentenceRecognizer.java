package com.converter.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SentenceRecognizer {

    private static final Logger logger = LoggerFactory.getLogger(SentenceRecognizer.class);

    /**
     * Checks if given state is considered as a sentence.
     *
     * @return true - sentence recognized, else false
     */
    public boolean isSentence(char letter, String sentence) {
        boolean result = false;
        if (letter == '!' || letter == '?') {
            result = true;
        } else if (letter == '.' && languageExceptions(sentence)) {
            result = true;
        }

        if (result) {
            logger.trace("Sentence: \"{}\" recogonized as: {}", sentence, result);
        }
        return result;
    }

    private boolean languageExceptions(String sentence) {
        return !sentence.endsWith("Mr") && !sentence.endsWith("Mrs") && !sentence.endsWith("Ms");
    }
}
