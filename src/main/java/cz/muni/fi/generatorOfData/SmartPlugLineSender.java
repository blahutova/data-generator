package cz.muni.fi.generatorOfData;

/**
 * Line sender for data from Smart Plugs devices.
 *
 * Created by Lucka on 10.2.2015.
 */
public class SmartPlugLineSender implements LineSender {

    @Override
    public void send(DataLine dataLine) {
        System.out.println(dataLine.toString());
    }

    public SmartPlugLineSender() {}
}
