package cz.muni.fi.data_generator;

import cz.muni.fi.data_generator.generator.DataLine;
import cz.muni.fi.data_generator.generator.Generator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class GeneratorTest {
    private List<DataLine> sortedList;
    private List<DataLine> shortList;
    private TestLineSender lineSender;

    @Before
    public void setUp() throws Exception {
        lineSender = new TestLineSender();
        String[] array = new String[1];
        array[0] = "hello";
        sortedList = new ArrayList<DataLine>();
        sortedList.add(new DataLine(1l, 1000l, array));
        sortedList.add(new DataLine(2l, 1000l, array));
        sortedList.add(new DataLine(3l, 2000l, array));
        sortedList.add(new DataLine(4l, 3000l, array));
        sortedList.add(new DataLine(5l, 4000l, array));
        sortedList.add(new DataLine(6l, 5000l, array));


        shortList = new ArrayList<DataLine>();
        shortList.add(new DataLine(1l, 1l, array));
        shortList.add(new DataLine(2l, 1l, array));
    }

    @Test
    public void testTimeBetweenTimestamps() throws Exception {
        TestLineSource source = new TestLineSource(sortedList);
        Generator generator = new Generator(source, lineSender);
        Long timeBefore = System.currentTimeMillis();
        generator.start();
        Long timeAfter = System.currentTimeMillis();
        assertEquals(((timeAfter - timeBefore)), sortedList.get(5).getTimestamp() - sortedList.get(0).getTimestamp(), 1);
    }

    @Test
    public void testTimeBetweenTimestampsWithSpeed() throws Exception {
        TestLineSource source = new TestLineSource(sortedList);
        Generator generator = new Generator(source, lineSender);
        double speed = 0.5;
        Long timeBefore = System.currentTimeMillis();
        generator.startWithSpeed(speed);
        Long timeAfter = System.currentTimeMillis();
        assertEquals((timeAfter - timeBefore), (sortedList.get(5).getTimestamp() - sortedList.get(0).getTimestamp()) * speed, 1);
    }

    @Test
    public void testTimeWithZeroSpeed() throws Exception{
        double speed = 0;
        TestLineSource source = new TestLineSource(sortedList);
        Generator generator = new Generator(source, lineSender);
        try {
            generator.startWithSpeed(speed);
            fail("Didn' t throw IllegalArgumentException for zero speed");
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testNextLineWithEmptyLineSource() throws Exception {
        List<DataLine> emptyList = new ArrayList<DataLine>();
        TestLineSource emptySource = new TestLineSource(emptyList);
        Generator generator = new Generator(emptySource, lineSender);
        try {
            generator.start();
        } catch (NullPointerException e) {
            fail("Thrown NullPointerException for empty source");
        }
    }

    @Test
    public void testSetToStart() throws Exception {
        TestLineSource shortSource = new TestLineSource(shortList);
        Generator generator = new Generator(shortSource, lineSender);
        Long timeBefore = System.currentTimeMillis();
        generator.start();
        generator.start();
        Long timeAfter = System.currentTimeMillis();
        assertEquals(timeAfter-timeBefore, 2 * (shortList.get(1).getTimestamp() - shortList.get(0).getTimestamp()));
    }
}