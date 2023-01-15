package com.converter.parser;

public enum FileFormat {
    XML("xml"), CSV("csv");

    private String format;

    FileFormat(String format) {
        this.format = format;
    }
}
