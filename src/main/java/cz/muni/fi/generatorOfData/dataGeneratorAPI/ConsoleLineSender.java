package cz.muni.fi.generatorOfData.dataGeneratorAPI;

/**
 * Line sender for sending data to console.
 *
 * Created by Lucka on 10.2.2015.
 */
public class ConsoleLineSender implements LineSender {

    @Override
    public void send(DataLine dataLine) {
        System.out.println(dataLine.toString());
    }

    public ConsoleLineSender() {}
}
