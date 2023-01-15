package com.converter;

import java.io.IOException;

import javax.inject.Inject;
import javax.xml.stream.XMLStreamException;

import com.converter.export.SupportExporter;
import com.converter.parser.FileFormat;
import com.converter.parser.SentenceParser;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Application {

    @Inject
    private SupportExporter supporter;

    @Inject
    private SentenceParser parser;

    public static void main(String[] args) throws IOException, XMLStreamException {
        if (args.length == 0) {
            System.err.println("Please provide the desired output format (xml|csv)");
            System.exit(0);
        }

        FileFormat format = FileFormat.XML;
        try {
            format = FileFormat.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException ex) {
            System.err.println("Please provide valid output format (xml|csv)");
            System.exit(0);
        }

        Injector injector = Guice.createInjector(new ConverterModule());
        Application app = injector.getInstance(Application.class);

        app.run(format);
    }

    void run(FileFormat format) {
        supporter.load();
        parser.parse(System.in, System.out, format);
    }

}