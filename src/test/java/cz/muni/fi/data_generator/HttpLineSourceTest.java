package cz.muni.fi.data_generator;

import cz.muni.fi.data_generator.generator.HTTPLineSource;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lucka on 28.3.2015.
 */
public class HttpLineSourceTest {
    private HTTPLineSource httpLineSource;
    private String textWithUnsortedLines;
    Iterator<CSVRecord> csvRecordIterator;
    private TestLineSender lineSender;

    @Before
    public void setUp() throws Exception {
        httpLineSource = new HTTPLineSource();
    }

    @Test
    public void testParseToTimestamp() throws Exception {
        String dateFromFile = "[30:23:49:10]";
        long timestamp = httpLineSource.parseToTimestamp(dateFromFile);
        assertEquals(809826550000l, timestamp);
    }

    @Test
    public void getDayOfTracingTest() throws Exception {
        String firstDay = "29";
        assertEquals(29, httpLineSource.getDay(firstDay));

    }

}
