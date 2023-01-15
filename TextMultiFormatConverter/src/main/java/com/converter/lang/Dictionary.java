package com.converter.lang;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class Dictionary {

    private static final Logger logger = LoggerFactory.getLogger(Dictionary.class);

    private Map<Short, String> dictionary;
    private Map<String, Short> reverseDictionary;
    private short index;

    @Inject
    public void init() {
        dictionary = new HashMap<>();
        reverseDictionary = new HashMap<>();
        index = 0;
        logger.debug("Dictionary initialized.");
    }

    private short addWord(String word) {
        dictionary.put(++index, word);
        reverseDictionary.put(word, index);
        logger.trace("Added word \"{}\" with index: {}", word, index);
        return index;
    }

    public String getWord(short index) {
        return dictionary.get(index);
    }

    public short getWordIndex(String word) {
        short wordIndex;
        if(!dictionary.containsValue(word)) {
            wordIndex = addWord(word);
        } else {
            wordIndex = reverseDictionary.get(word);
        }
        logger.trace("Word \"{}\" index = {}", word, wordIndex);
        return wordIndex;
    }
}
