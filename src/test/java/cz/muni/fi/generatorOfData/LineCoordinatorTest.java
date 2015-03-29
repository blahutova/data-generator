package cz.muni.fi.generatorOfData;

import cz.muni.fi.generatorOfData.dataGeneratorAPI.DataLine;
import cz.muni.fi.generatorOfData.dataGeneratorAPI.Generator;
import cz.muni.fi.generatorOfData.dataGeneratorAPI.LineSource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by lucka on 28.3.2015.
 */
public class LineCoordinatorTest {
    private List<DataLine> unsortedList;
    private List<DataLine> unsortedListWithoutOrder;
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
