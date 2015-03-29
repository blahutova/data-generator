package cz.muni.fi.generatorOfData;

import cz.muni.fi.generatorOfData.dataGeneratorAPI.DataLine;
import cz.muni.fi.generatorOfData.dataGeneratorAPI.LineSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Line sender for testing.
 *
 * Created by lucka on 17.2.2015.
 */
public class TestLineSender implements LineSender {
    private List<DataLine> listOfLines = new ArrayList<DataLine>();

    public List<DataLine> getListOfLines() {
        return listOfLines;
    }

    @Override
    public void send(DataLine dataLine) {
        listOfLines.add(dataLine);
    }

    public TestLineSender() {}
}
