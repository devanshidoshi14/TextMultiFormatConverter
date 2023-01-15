package com.converter.export;

import java.io.OutputStream;

import com.converter.parser.SentenceIterator;

public interface FileExporter {

    // Exports given SentenceIterator to OutputStream using XMLStreamWriter
    void export(OutputStream out, SentenceIterator sentences, int maxWordsInSentence);

}
