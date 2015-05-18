package cz.muni.fi.data_generator.generator;

/**
 * Interface for sending one line of data in the way of user's needs.
 * Line can be sent to the console, another application etc.
 *
 */
public interface LineSender {
    /**
     * Method, which is sending given DataLine to output.
     * @param dataLine DataLine for sending to output
     */
    void send(DataLine dataLine);
}
