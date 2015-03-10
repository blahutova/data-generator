package cz.muni.fi.generatorOfData;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Line source for testing.
 *
 * Created by Lucka on 17.2.2015.
 */
public class TestLineSource implements LineSource{
    private List<DataLine> listOfLines;
    private LineCoordinator lineCoordinator;

    public TestLineSource(List<DataLine> listOfLines) {
        this.listOfLines = listOfLines;
        this.lineCoordinator = new LineCoordinator(listOfLines.iterator(), this);
    }

    @Override
    public DataLine nextLine() throws IOException {
        return lineCoordinator.nextLine();
    }

    public void setToStart() {
        lineCoordinator.setToStart(listOfLines.iterator());
    }

    @Override
    public DataLine makeDataLineFromLine(Object line) {
        return (DataLine) line;
    }
}
