package cz.muni.fi.generatorOfData.dataGeneratorAPI;

/**
 * Interface for sending one line of data in the way of user's needs.
 * Line can be sent to the console, another application etc.
 *
 * Created by Lucka on 10.2.2015.
 */
public interface LineSender {
    /**
     * Method, which is sending given DataLine to output.
     * @param dataLine DataLine for sending to output
     */
    void send(DataLine dataLine);
}
