package cz.muni.fi.generatorOfData;

import cz.muni.fi.generatorOfData.httpServerGenerator.HTTPLineSource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by lucka on 28.3.2015.
 */
public class HttpLineSourceTest {
    private HTTPLineSource httpLineSource;

    @Before
    public void setUp() throws Exception {
        httpLineSource = new HTTPLineSource();
    }

    @Test
    public void testParseToTimestamp() throws Exception {
        String dateFromFile = "[30:23:49:10]";
        long timestamp = httpLineSource.parseToTimestamp(dateFromFile);
        assertEquals(809826550, timestamp);
    }

    @Test
    public void getDayOfTracingTest() throws Exception {
        String firstDay = "29";
        assertEquals(29, httpLineSource.getDay(firstDay));

    }

}
