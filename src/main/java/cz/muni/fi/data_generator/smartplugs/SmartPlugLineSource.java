package cz.muni.fi.data_generator.smartplugs;

import cz.muni.fi.data_generator.generator.DataLine;
import cz.muni.fi.data_generator.generator.LineSource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Line source from file with information about activity of Smart Plugs. A smart plug plays a role of a proxy
 * between the wall power outlet and the device connected to it. It is equipped with a range of sensors
 * which measure different, power consumption related, values.
 *
 * Source:
 * http://www.cse.iitb.ac.in/debs2014/?page_id=42
 *
 * Created by Lucka on 10.2.2015.
 */
public class SmartPlugLineSource implements LineSource {
    private final static int POSITION_OF_TIMESTAMP = 1;
    private final static int POSITION_OF_ORDER = 0;
    private final static int NUMBER_OF_DATA_FIELDS = 6;
    private final static int MAX_SIZE_OF_BUFFER = 1000;
    private Iterator<CSVRecord> csvParserIterator;
    private CSVParser parser;
    private File pathToCsv;
    private Queue<DataLine> sortedBufferOfLines = new PriorityQueue<DataLine>();

    public SmartPlugLineSource(File pathToCsv) throws IOException {
        this.pathToCsv = pathToCsv;
        this.parser = CSVParser.parse(pathToCsv, Charset.defaultCharset(), CSVFormat.DEFAULT);
        this.csvParserIterator = parser.iterator();
        initializeSortedBufferOfLines();
    }

    @Override
    public DataLine nextLine() {
        if (sortedBufferOfLines.size() != 0) {
            if (csvParserIterator.hasNext()) {
                sortedBufferOfLines.add(makeDataLineFromLine(csvParserIterator.next()));
            }
            return sortedBufferOfLines.poll();
        } else {
            return null;
        }
    }

    @Override
    public void setToStart() throws IOException {
        this.parser = CSVParser.parse(pathToCsv, Charset.defaultCharset(), CSVFormat.DEFAULT);
        this.csvParserIterator = parser.iterator();
        sortedBufferOfLines.clear();
        initializeSortedBufferOfLines();
    }

    public DataLine makeDataLineFromLine(CSVRecord line) {
            return new DataLine(Long.parseLong(line.get(POSITION_OF_ORDER)), Long.parseLong(line.get(POSITION_OF_TIMESTAMP)) * 1000, getDataFields(line));
    }

    private String[] getDataFields(CSVRecord record) {
        String[] data = new String[NUMBER_OF_DATA_FIELDS];
        int iteratorForData = 0;
        for (int i = 0; i < NUMBER_OF_DATA_FIELDS; i++) {
            if (i == POSITION_OF_TIMESTAMP || i == POSITION_OF_ORDER) continue;
            data[iteratorForData] = record.get(i);
            iteratorForData++;
        }
        return data;
    }

    private void initializeSortedBufferOfLines() {
        while (csvParserIterator.hasNext() && sortedBufferOfLines.size() < MAX_SIZE_OF_BUFFER) {
            sortedBufferOfLines.add(makeDataLineFromLine(csvParserIterator.next()));
        }
    }

    @Override
    public void close() throws IOException {
        parser.close();
    }
}
