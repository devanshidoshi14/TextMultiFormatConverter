package com.converter.parser;

import static com.converter.parser.FileFormat.CSV;
import static com.converter.parser.FileFormat.XML;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordsExtractor {

    private static final Logger logger = LoggerFactory.getLogger(WordsExtractor.class);

    private FileFormat mode;

    public WordsExtractor setMode(FileFormat mode) {
        this.mode = mode;
        return this;
    }

    // Extracts given sentence to words.
    public List<String> extract(String sentence) {
        List<String> words = new ArrayList<>();
        if (mode == XML) {
            words = extractForXML(sentence);
        } else if (mode == CSV) {
            words = extractForCSV(sentence);
        }
        return words;
    }

    private String[] split(String sentence) {
        return sentence.replaceAll(",([^ ])", ", $1").split("\\s+");
    }

    /**
     * Sentence extraction for XML purposes. Removes : ! , ? ( ) from word and
     * changes ' ` ’ to encoded entity &amp;apos; Removes single characters like -
     */
    private List<String> extractForXML(String sentence) {
        List<String> words = new ArrayList<>();
        String[] wordsArr = split(sentence);

        for (String w : wordsArr) {
            if (!w.trim().isEmpty()) {
                String word = w.trim().replaceAll("[:!,\\?\\(\\)]", "").replaceAll("['`’]", "&apos;");
                if (word.length() > 1 || word.matches("[a-zA-Z]")) {
                    words.add(word);
                }
            }
        }
        logger.trace("Extracted words: {}. From sentence: {}", words, sentence);
        return words;
    }

    /**
     * Sentence extraction for CSV purposes. Removes : ! , ? ( ) from word and
     * normalizes ' ` ’ to ' Removes single characters like -
     *
     */
    private List<String> extractForCSV(String sentence) {
        List<String> words = new ArrayList<>();
        String[] wordsArr = split(sentence);

        for (String w : wordsArr) {
            if (!w.trim().isEmpty()) {
                String word = w.trim().replaceAll("[:!,\\?\\(\\)]", "").replaceAll("['`’]", "'");
                if (word.length() > 1 || word.matches("[a-zA-Z]")) {
                    words.add(word);
                }
            }
        }
        logger.trace("Extracted words: {}. From sentence: {}", words, sentence);
        return words;
    }
}
