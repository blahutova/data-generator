package cz.muni.fi.generatorOfData.httpServerGenerator;

import cz.muni.fi.generatorOfData.dataGeneratorAPI.DataLine;
import cz.muni.fi.generatorOfData.dataGeneratorAPI.LineCoordinator;
import cz.muni.fi.generatorOfData.dataGeneratorAPI.LineSource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Line source for data of file epa-http, which contains a day's worth of all HTTP requests to the EPA WWW server
 * located at Research Triangle Park, NC.
 *
 * The logs are an ASCII file with one line per request, with the following columns:
 * - host making the request. A hostname when possible, otherwise the Internet address if the name could not be looked up.
 * - date in the format "[DD:HH:MM:SS]", where DD is either "29" or "30" for August 29 or August 30 in year 1995, respectively, and HH:MM:SS is the time of day using a 24-hour clock.
 * - request given in quotes.
 * - HTTP reply code.
 * - bytes in the reply.
 *
 * Source:
 * http://ita.ee.lbl.gov/html/contrib/EPA-HTTP.html
 *
 * Created by Lucka on 25.2.2015.
 */
public class HTTPLineSource implements LineSource {
    private final static int YEAR_OF_TRACING = 1995;
    private final static int FIRST_DAY_OF_TRACING = 29;
    private final static int SECOND_DAY_OF_TRACING = 30;
    private final static int POSITION_OF_TIMESTAMP = 1;
    private Iterator<CSVRecord> csvParserIterator;
    private CSVParser parser;
    private File pathToCsv;
    private LineCoordinator lineCoordinator;

    public HTTPLineSource() {
    }

    public HTTPLineSource(File pathToCsv) throws IOException {
        this.pathToCsv = pathToCsv;
        this.parser = CSVParser.parse(pathToCsv, Charset.defaultCharset(), CSVFormat.newFormat(' '));
        this.csvParserIterator = parser.iterator();
        this.lineCoordinator = new LineCoordinator(csvParserIterator, this);
    }

    @Override
    public DataLine nextLine() {
        return lineCoordinator.nextLine();
    }

    @Override
    public void setToStart() throws IOException {
        this.parser = CSVParser.parse(pathToCsv, Charset.defaultCharset(), CSVFormat.newFormat(' '));
        this.csvParserIterator = parser.iterator();
        lineCoordinator.setToStart(csvParserIterator);
    }

    @Override
    public DataLine makeDataLineFromLine(Object line) {
        CSVRecord record = (CSVRecord) line;
        return new DataLine(parseToTimestamp(record.get(POSITION_OF_TIMESTAMP)), getDataFields(record));
    }

    public Long parseToTimestamp(String date){
        String dateWithoutBrackets = date.substring(1, date.length() - 1);
        String[] partsOfDate =  dateWithoutBrackets.split(":");
        return getTimestampFromDate(partsOfDate);
    }

    public Long getTimestampFromDate(String[] partsOfDate) {
        Calendar timestamp = new GregorianCalendar();
        timestamp.clear();
        timestamp.setTimeZone(TimeZone.getTimeZone("GMT"));
        timestamp.set(Calendar.DAY_OF_MONTH, getDay(partsOfDate[0]));
        timestamp.set(Calendar.YEAR, YEAR_OF_TRACING);
        timestamp.set(Calendar.MONTH, Calendar.AUGUST);
        timestamp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(partsOfDate[1]));
        timestamp.set(Calendar.MINUTE, Integer.parseInt(partsOfDate[2]));
        timestamp.set(Calendar.SECOND, Integer.parseInt(partsOfDate[3]));
        return timestamp.getTimeInMillis() / 1000;
    }

    public int getDay(String dayOfTracing){
        if (dayOfTracing.equals(Integer.toString(FIRST_DAY_OF_TRACING))) {
            return FIRST_DAY_OF_TRACING;
        } else {
            return SECOND_DAY_OF_TRACING;
        }
    }

    public String[] getDataFields(CSVRecord record) {
        String[] data = new String[record.size()-1];
        int iteratorForData = 0;
        for (int i = 0; i < record.size(); i++) {
            if (i == POSITION_OF_TIMESTAMP) continue;
            data[iteratorForData] = record.get(i);
            iteratorForData++;
        }
        return data;
    }

    @Override
    public void close() throws IOException {
        parser.close();
    }
}
