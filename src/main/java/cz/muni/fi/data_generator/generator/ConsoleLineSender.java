package cz.muni.fi.data_generator.generator;

/**
 * Line sender for sending data to console.
 *
 */
public class ConsoleLineSender implements LineSender {

    @Override
    public void send(DataLine dataLine) {
        System.out.println(dataLine.toString());
    }

    public ConsoleLineSender() {}
}
