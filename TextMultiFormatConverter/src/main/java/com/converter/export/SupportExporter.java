package com.converter.export;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.converter.parser.FileFormat;

@Singleton
public class SupportExporter {

    private static final Logger logger = LoggerFactory.getLogger(SupportExporter.class);

    private Map<FileFormat, FileExporter> exporters;

    public void load() {
        exporters = new HashMap<>(2);
        exporters.put(FileFormat.XML, new XMLExporter());
        exporters.put(FileFormat.CSV, new CSVExporter());
        logger.debug("SupportExporter loaded.");

    }

    public FileExporter getExporter(FileFormat format) {
        return exporters.get(format);
    }

    public long registeredExportersSize() {
        return exporters.size();
    }

    public void registerExporter(FileFormat format, FileExporter writer) {
        if (exporters.containsKey(format) || exporters.containsValue(writer)) {
            throw new IllegalArgumentException("Exporter already registered!");
        }
        exporters.put(format, writer);
        logger.debug("New writer registered for format: {}", format);
    }

    public void unregisterExporter(FileFormat format) {
        exporters.remove(format);
    }

}
