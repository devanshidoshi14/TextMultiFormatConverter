package com.converter;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.converter.export.SupportExporter;
import com.converter.lang.Dictionary;
import com.converter.parser.FileFormat;
import com.converter.parser.SentenceIterator;

public class CSVExporterTest {

    private static SupportExporter exporter;

    @BeforeClass
    public static void configure() {
        exporter = new SupportExporter();
        exporter.load();
    }

    @Test
    public void shouldExportToCSV() {
        List<short[]> sentences = new ArrayList<>();
        sentences.add(new short[] { 1, 2, 3, 4, 5, 6 });
        sentences.add(new short[] { 7, 2, 3, 8, 9, 6 });

        Dictionary dictionary = new Dictionary();
        dictionary.init();
        dictionary.getWordIndex("one");
        dictionary.getWordIndex("two");
        dictionary.getWordIndex("three");
        dictionary.getWordIndex("four");
        dictionary.getWordIndex("five");
        dictionary.getWordIndex("six");
        dictionary.getWordIndex("seven");
        dictionary.getWordIndex("eight");
        dictionary.getWordIndex("nine");

        SentenceIterator sentenceIterator = new SentenceIterator(sentences, dictionary);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.getExporter(FileFormat.CSV).export(baos, sentenceIterator, 6);

        String expect = ",Word 1,Word 2,Word 3,Word 4,Word 5,Word 6\n" + "Sentence 1,one,two,three,four,five,six\n"
                + "Sentence 2,seven,two,three,eight,nine,six\n";
        assertEquals(expect, new String(baos.toByteArray(), StandardCharsets.UTF_8));
    }
}