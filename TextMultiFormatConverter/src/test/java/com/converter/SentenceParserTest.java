package com.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.xmlunit.matchers.CompareMatcher.isIdenticalTo;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.xmlunit.builder.Input;

import com.converter.export.SupportExporter;
import com.converter.lang.Dictionary;
import com.converter.lang.SentenceRecognizer;
import com.converter.parser.FileFormat;
import com.converter.parser.SentenceParser;
import com.converter.parser.WordsExtractor;

public class SentenceParserTest {

    private SentenceParser parser;

    @Before
    public void setUp() {
        Dictionary dict = new Dictionary();
        dict.init();
        SupportExporter exporter = new SupportExporter();
        exporter.load();
        parser = new SentenceParser(dict, exporter, new SentenceRecognizer(), new WordsExtractor());
    }

    @Test
    public void shouldParseTextToXml() throws FileNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        parser.parse(new FileInputStream(Paths.get("src/test/resources/small.in").toFile()), baos, FileFormat.XML);

        assertThat(Input.fromString(new String(baos.toByteArray(), StandardCharsets.UTF_8)),
                isIdenticalTo(Input.fromFile("src/test/resources/small.xml")));
    }

    @Test
    public void shouldParseTextToCsv() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        parser.parse(new FileInputStream(Paths.get("src/test/resources/small.in").toFile()), baos, FileFormat.CSV);

        String expect = new String(Files.readAllBytes(Paths.get("src/test/resources/small.csv")));
        String result = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        assertEquals(expect, result);
    }
}