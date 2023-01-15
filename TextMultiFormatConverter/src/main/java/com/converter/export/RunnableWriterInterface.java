package com.converter.export;

import javax.xml.stream.XMLStreamException;

@FunctionalInterface
public interface RunnableWriterInterface {

    void run() throws XMLStreamException;;

}
