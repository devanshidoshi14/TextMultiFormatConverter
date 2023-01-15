package com.converter.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.converter.export.SupportExporter;
import com.converter.lang.Dictionary;
import com.converter.lang.SentenceRecognizer;
import com.converter.lang.WordSorter;

public class SentenceParser {

    private static final Logger logger = LoggerFactory.getLogger(SentenceParser.class);

    private Dictionary dictionary;
    private SupportExporter supporter;
    private SentenceRecognizer sentenceRecognizer;
    private WordsExtractor wordsExtractor;

    private List<short[]> sentences = new ArrayList<>(25);
    private int maxWordsInSentence = 0;

    @Inject
    public SentenceParser(Dictionary dictionary, SupportExporter supporter, SentenceRecognizer sentenceRecognizer,
            WordsExtractor wordsExtractor) {
        this.dictionary = dictionary;
        this.supporter = supporter;
        this.sentenceRecognizer = sentenceRecognizer;
        this.wordsExtractor = wordsExtractor;
    }

    // Processing the input to given format output.

    public void parse(InputStream in, OutputStream out, FileFormat format) {
        logger.debug("Parsing - start");
        read(in, format);
        logger.debug("Parsing - input read successful, starting export: {}", format);
        export(out, format);
        logger.debug("Parsing - done");
    }

    // Read input stream

    private void read(InputStream in, FileFormat format) {
        try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            int c;
            char letter;
            StringBuilder sb = new StringBuilder();

            while ((c = reader.read()) != -1) {
                letter = (char) c;
                if (sentenceRecognizer.isSentence(letter, sb.toString())) {
                    processSentence(sb.toString(), format);
                    sb = new StringBuilder();
                    continue;
                }
                sb.append(letter);
            }
        } catch (IOException e) {
            logger.error("Error during reading input: {}", e);
            System.exit(-1);
        }
    }

    // Processes a single sentence.
    private void processSentence(String sentence, FileFormat format) {
        List<String> words = wordsExtractor.setMode(format).extract(sentence);
        saveLongestWordsCount(words);
        words.sort(WordSorter.alphabeticalCapitalLsst);
        short[] indexes = toIndexes(words);
        sentences.add(indexes);
        logger.trace("Processed sentence: {}, word: {}, indexes: {}", sentence, words, indexes);
    }

    // Transforms words to their indexes.
    private short[] toIndexes(List<String> words) {
        short[] wordsIndexed = new short[words.size()];
        int i = 0;
        for (String word : words) {
            wordsIndexed[i++] = dictionary.getWordIndex(word);
        }
        return wordsIndexed;
    }

    // Saves the longest word count in sentences.
    private void saveLongestWordsCount(List<String> words) {
        if (words.size() > maxWordsInSentence) {
            maxWordsInSentence = words.size();
        }
    }

    // Exports parsed data for given format output.
    private void export(OutputStream out, FileFormat format) {
        supporter.getExporter(format).export(out, new SentenceIterator(sentences, dictionary), maxWordsInSentence);
    }

}
