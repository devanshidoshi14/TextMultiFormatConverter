package com.converter;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.converter.parser.FileFormat;
import com.converter.parser.WordsExtractor;

public class WordsExtractorTest {

    private WordsExtractor extractor;

    @Before
    public void setUp() {
        extractor = new WordsExtractor();
    }

    @After
    public void tearDown() {
        extractor = null;
    }

    @Test
    public void souldExtract3wordsForXML() {
        extractor.setMode(FileFormat.XML);

        List<String> extract = extractor.extract("How happy life");
        assertEquals(extract, Stream.of("How", "happy", "life").collect(Collectors.toList()));

        extract = extractor.extract("How,happy , life");
        assertEquals(extract, Stream.of("How", "happy", "life").collect(Collectors.toList()));

        extract = extractor.extract("you'd you`d you’d");
        assertEquals(extract, Stream.of("you&apos;d", "you&apos;d", "you&apos;d").collect(Collectors.toList()));

        extract = extractor.extract("clear, commas, please,");
        assertEquals(extract, Stream.of("clear", "commas", "please").collect(Collectors.toList()));

        extract = extractor.extract("你这肮脏的掠夺者 你这肮脏 的掠夺者");
        assertEquals(extract, Stream.of("你这肮脏的掠夺者", "你这肮脏", "的掠夺者").collect(Collectors.toList()));

        extract = extractor.extract("How   happy     life         ");
        assertEquals(extract, Stream.of("How", "happy", "life").collect(Collectors.toList()));
    }

    @Test
    public void shouldExtract3wordsForCSV() {
        extractor.setMode(FileFormat.CSV);

        List<String> extract = extractor.extract("How happy life");
        assertEquals(extract, Stream.of("How", "happy", "life").collect(Collectors.toList()));

        extract = extractor.extract("How,happy , life");
        assertEquals(extract, Stream.of("How", "happy", "life").collect(Collectors.toList()));

        extract = extractor.extract("you'd you`d you’d");
        assertEquals(extract, Stream.of("you'd", "you'd", "you'd").collect(Collectors.toList()));

        extract = extractor.extract("clear, commas, please,");
        assertEquals(extract, Stream.of("clear", "commas", "please").collect(Collectors.toList()));

        extract = extractor.extract("你这肮脏的掠夺者 你这肮脏 的掠夺者");
        assertEquals(extract, Stream.of("你这肮脏的掠夺者", "你这肮脏", "的掠夺者").collect(Collectors.toList()));

        extract = extractor.extract("How   happy     life         ");
        assertEquals(extract, Stream.of("How", "happy", "life").collect(Collectors.toList()));
    }
}