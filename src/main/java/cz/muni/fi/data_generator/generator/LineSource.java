package cz.muni.fi.data_generator.generator;

import java.io.IOException;

/**
 * Interface for working with source (usually file) of data. The implementations are able to parse
 * data and create DataLine objects from lines of source, so they can be sent to appropriate
 * LineSender.
 *
 * Created by Lucka on 10.2.2015.
 */
public interface LineSource {
    /**
     * Method, which returns next line from source (it doesn't repair
     * possible mistakes in order of lines).
     *
     * @return next line from source
     * @throws IOException if something is wrong with parsing the lines from source
     */
    public DataLine nextLine() throws IOException;

    /**
     * With this method is LineSource able to start iterating
     * the lines all over from the beginning. It's necessary, when you want
     * to use Generator more than once.
     *
     * @throws IOException if something is wrong with parsing the lines from source
     */
    public void setToStart() throws IOException;

    /**
     * Method for closing stream, from which was created LineSource.
     *
     * @throws IOException
     */
    public void close() throws IOException;
}
