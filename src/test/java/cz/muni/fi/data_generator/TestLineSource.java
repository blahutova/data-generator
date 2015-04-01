package cz.muni.fi.data_generator;

import cz.muni.fi.data_generator.generator.DataLine;
import cz.muni.fi.data_generator.generator.LineSource;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Line source for testing.
 *
 * Created by Lucka on 17.2.2015.
 */
public class TestLineSource implements LineSource {
    private List<DataLine> listOfLines;
    private Iterator<DataLine> linesIterator;

    public TestLineSource(List<DataLine> listOfLines) {
        this.listOfLines = listOfLines;
        this.linesIterator = listOfLines.iterator();
    }

    @Override
    public DataLine nextLine() throws IOException {
        if (linesIterator.hasNext()) {
            return linesIterator.next();
        } else {
            return null;
        }
    }

    public void setToStart() {
        this.linesIterator = listOfLines.iterator();
    }


    @Override
    public void close() throws IOException {

    }
}
