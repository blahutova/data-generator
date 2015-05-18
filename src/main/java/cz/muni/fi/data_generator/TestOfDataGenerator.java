package cz.muni.fi.data_generator;

import cz.muni.fi.data_generator.generator.DataLine;
import cz.muni.fi.data_generator.generator.LineSender;
import cz.muni.fi.data_generator.generator.LineSource;

import java.io.IOException;

/**
 * Class for testing sending function of generator.<br/><br/>
 *
 * Example usage:<br/>
 * //create object TestOfDataGenerator<br/>
 * TestOfDataGenerator generator = new TestOfDataGenerator(lineSource, lineSender);<br/>
 * //try sending and watch numbers in output<br/>
 * generator.start();<br/>
 */
public class TestOfDataGenerator {
    private LineSource source;
    private LineSender lineSender;

    /**
     * Constructor for Generator class. You must provide concrete LineSource and
     * LineSender of data.
     *
     * @param source LineSource created for data of given file
     * @param lineSender LineSender created for data of given file
     */
    public TestOfDataGenerator (LineSource source, LineSender lineSender) {
        this.source = source;
        this.lineSender = lineSender;
    }

    /**
     * Method, which starts sending data from LineSource with given LineSender. Data
     * are sent in real-time speed, which indicate their timestamps.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void start() throws IOException, InterruptedException {
        startSendingData(1);
    }

    /**
     * Method, which starts sending data from LineSource with given LineSender. Data
     * are sent in user-defined speed. It can accelerate or slow down the speed, which
     * indicate timestamps of lines.
     *
     * @param speedCoefficient coefficient, which can can accelerate or slow down the speed of sending data
     * @throws IOException
     * @throws InterruptedException
     */
    public void startWithSpeed(double speedCoefficient) throws IOException, InterruptedException {
        if (speedCoefficient != 0) {
            startSendingData(speedCoefficient);
        } else {
            throw new IllegalArgumentException("Speed cant' t be zero!");
        }
    }

    private void startSendingData(double speedCoefficient) throws IOException, InterruptedException {
        if (lineSender == null) throw new NullPointerException("Line sender is null");
        if (source == null) throw new NullPointerException("Line source is null");
        DataLine firstLine = source.nextLine();
        DataLine secondLine;
        while (firstLine != null) {
            //lineSender.send(firstLine);
            Long timeBefore = System.currentTimeMillis();
            if ((secondLine = source.nextLine()) == null) {
                break;
            }
            Thread.sleep(timeBetweenTwoLines(firstLine, secondLine, speedCoefficient));
            System.out.println("Real time between lines: " + (System.currentTimeMillis() - timeBefore) + " Time between lines in file: " + timeBetweenTwoLines(firstLine, secondLine, speedCoefficient));
            firstLine = secondLine;
        }
        source.setToStart();
    }

    public Long timeBetweenTwoLines(DataLine firstLine, DataLine secondLine, double speedCoefficient) {
        return (long) ((secondLine.getTimestamp() - firstLine.getTimestamp()) * speedCoefficient);
    }
}
