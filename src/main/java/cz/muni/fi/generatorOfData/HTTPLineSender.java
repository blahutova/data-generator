package cz.muni.fi.generatorOfData;

/**
 * Line sender for data from file epa-http.
 *
 * Created by lucka on 25.2.2015.
 */
public class HTTPLineSender implements LineSender {
    @Override
    public void send(DataLine dataLine) {
        System.out.println(dataLine.toString());
    }

    public HTTPLineSender() {}
}
