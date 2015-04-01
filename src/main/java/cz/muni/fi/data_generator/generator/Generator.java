package cz.muni.fi.data_generator.generator;

import java.io.IOException;

/**
 * Class with basic methods for sending data. User can start sending data
 * with normal speed, or he can choose his own coefficient of speed.
 *
 * Created by Lucka on 10.2.2015.
 */
public class Generator {
    private LineSource source;
    private LineSender lineSender;

    /**
     * Constructor for Generator class. You must provide concrete LineSource and
     * LineSender of data.
     *
     * @param source LineSource created for data of given file
     * @param lineSender LineSender created for data of given file
     */
    public Generator(LineSource source, LineSender lineSender) {
        this.source = source;
        this.lineSender = lineSender;
    }

    /**
     * Method, which starts sending data from LineSource with given LineSender. Data
     * are sent in real-time speed, which is indicated by their timestamps.
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
     * Real speed is multiplied by given coefficient. So it means that if you want faster speed, you
     * must provide speed coefficient less than zero.
     *
     * For example:
     * Time between two lines is 1000 miliseconds. If you provide speed coefficient 0.01,
     * time between two lines will decrease to 10 miliseconds.
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
            lineSender.send(firstLine);
            if ((secondLine = source.nextLine()) == null) {
                break;
            }
            Thread.sleep(timeBetweenTwoLines(firstLine, secondLine, speedCoefficient));
            firstLine = secondLine;
        }
        source.setToStart();
    }

    public Long timeBetweenTwoLines(DataLine firstLine, DataLine secondLine, double speedCoefficient) {
        return (long) ((secondLine.getTimestamp() - firstLine.getTimestamp()) * speedCoefficient);
    }
}
