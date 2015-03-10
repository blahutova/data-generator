package cz.muni.fi.generatorOfData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class GeneratorTest {
    private List<DataLine> unsortedList;
    private List<DataLine> unsortedListWithoutOrder;
    private List<DataLine> shortList;
    private TestLineSender lineSender;

    @Before
    public void setUp() throws Exception {
        lineSender = new TestLineSender();
        String[] array = new String[1];
        array[0] = "hello";
        unsortedList = new ArrayList<DataLine>();
        unsortedList.add(new DataLine(1l, 1l, array));
        unsortedList.add(new DataLine(2l, 1l, array));
        unsortedList.add(new DataLine(3l, 2l, array));
        unsortedList.add(new DataLine(4l, 3l, array));
        unsortedList.add(new DataLine(5l, 2l, array));
        unsortedList.add(new DataLine(7l, 5l, array));
        unsortedList.add(new DataLine(6l, 5l, array));

        shortList = new ArrayList<DataLine>();
        shortList.add(new DataLine(1l, 1l, array));
        shortList.add(new DataLine(2l, 1l, array));

        unsortedListWithoutOrder = new ArrayList<DataLine>();
        unsortedListWithoutOrder.add(new DataLine(2l, array));
        unsortedListWithoutOrder.add(new DataLine(1l, array));
        unsortedListWithoutOrder.add(new DataLine(2l, array));
        unsortedListWithoutOrder.add(new DataLine(3l, array));
        unsortedListWithoutOrder.add(new DataLine(2l, array));
        unsortedListWithoutOrder.add(new DataLine(6l, array));
        unsortedListWithoutOrder.add(new DataLine(5l, array));
    }

    @Test
    public void testTimeBetweenTimestamps() throws Exception {
        TestLineSource source = new TestLineSource(unsortedList);
        Generator generator = new Generator(source, lineSender);
        Long timeBefore = System.currentTimeMillis();
        generator.start();
        Long timeAfter = System.currentTimeMillis();
        assertEquals(((timeAfter - timeBefore) / 1000), unsortedList.get(5).getTimestamp() - unsortedList.get(0).getTimestamp());
    }

    @Test
    public void testTimeBetweenTimestampsWithSpeed() throws Exception {
        TestLineSource source = new TestLineSource(unsortedList);
        Generator generator = new Generator(source, lineSender);
        double speed = 0.5;
        Long timeBefore = System.currentTimeMillis();
        generator.startWithSpeed(speed);
        Long timeAfter = System.currentTimeMillis();
        assertEquals(((timeAfter - timeBefore) / 1000), (unsortedList.get(5).getTimestamp() - unsortedList.get(0).getTimestamp()) * speed, 0.1);
    }

    @Test
    public void testTimeWithZeroSpeed() throws Exception{
        double speed = 0;
        TestLineSource source = new TestLineSource(unsortedList);
        Generator generator = new Generator(source, lineSender);
        try {
            generator.startWithSpeed(speed);
            fail("Didn' t throw IllegalArgumentException for zero speed");
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testNextLineWithEmptySmartPlugSource() throws Exception {
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

    @Test
    public void testOrderingOfLinesWithOrder() throws  Exception {
        LineSource unsortedSource = new TestLineSource(unsortedList);
        Generator generator = new Generator(unsortedSource, lineSender);
        generator.start();
        List<DataLine> sortedList = lineSender.getListOfLines();
        for (int i = 0; i < sortedList.size() - 1; i++) {
            assertTrue(sortedList.get(i).getTimestamp() <= sortedList.get(i + 1).getTimestamp());
        }
    }

    @Test
    public void testOrderingOfLinesWithoutOrder() throws  Exception {
        LineSource unsortedSource = new TestLineSource(unsortedListWithoutOrder);
        Generator generator = new Generator(unsortedSource, lineSender);
        generator.start();
        List<DataLine> sortedList = lineSender.getListOfLines();
        for (int i = 0; i < sortedList.size() - 1; i++) {
            assertTrue(sortedList.get(i).getTimestamp() <= sortedList.get(i + 1).getTimestamp());
        }
    }
}