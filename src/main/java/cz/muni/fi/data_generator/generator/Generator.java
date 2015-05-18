package cz.muni.fi.data_generator.generator;

import cz.muni.fi.data_generator.distributed_sending.Connection;
import cz.muni.fi.data_generator.distributed_sending.Server;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.util.*;

/**
 * Basic class for sending data. User can start sending data
 * with normal speed, or he can choose his own coefficient of speed. Also he can
 * send data to more computers than one. <br /><br />
 *
 * Example usage:<br />
 * //provide the path to your file with data <br />
 * File csv = new File("/path/to/file.csv"); <br />
 * //create your implementation of LineSource and LineSender <br />
 * HTTPLineSource lineSource = new HTTPLineSource(csv);<br />
 * HTTPLineSender lineSender = new HTTPLineSender();<br />
 * //create Generator with LineSource and LineSender<br />
 * Generator generator = new Generator(lineSource, lineSender); <br />
 * //start sending data to output<br />
 * generator.startWithSpeed(0.5);<br />
 * //don't forget to close the LineSource<br />
 * lineSource.close();<br /><br />
 *
 * Sending output to more computers: <br />
 * //create array with ports and hostnames of your computers<br />
 * String[][] portsAndHostNames = {<br />
 * {"1024", "localhost"},<br />
 * {"1025", "localhost"},<br />
 * {"1026", "localhost"},<br />
 * {"1027", "localhost"},<br />
 * };<br />
 * //set ports and host names for generator<br />
 * generator.setPortsAndHostNames(portsAndHostNames);<br />
 * //start sending <br />
 * generator.startDistributedSending();<br />
 *
 */
public class Generator extends TimerTask {
    private LineSource source;
    private LineSender lineSender;
    private String[][] portsAndHostNames;

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

    /**
     * Method for sending data to more computers. First, you must create
     * array of names of ports and hosts and then set it in generator. Then you
     * can start this method and send data to many computers.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void startDistributedSending() throws IOException, InterruptedException {
        createServersAccordingToPorts(portsAndHostNames);
        DataLine firstLine = source.nextLine();
        DataLine secondLine;
        int currentPC = 0;
        while (firstLine != null) {
            if (currentPC == portsAndHostNames.length) {
                currentPC = 0;
            }
            int currentPort = Integer.parseInt(portsAndHostNames[currentPC][0]);
            String currentHostName = portsAndHostNames[currentPC][1];
            sendAndReadDataFromServer(currentPort, currentHostName, firstLine, currentPC);
            currentPC++;
            if ((secondLine = source.nextLine()) == null) {
                break;
            }
            Thread.sleep(timeBetweenTwoLines(firstLine, secondLine, 1));
            firstLine = secondLine;
        }
    }

    private void createServersAccordingToPorts(String[][] portsAndHostNames) {
        HashSet<Server> listOfServers = new HashSet<Server>(portsAndHostNames.length);
        for (int i = 0; i < portsAndHostNames.length; i++){
            listOfServers.add(new Server(Integer.parseInt(portsAndHostNames[i][0])));
        }
    }

    private void sendAndReadDataFromServer(int port, String hostName, DataLine line, int currentPC) {
        Connection connection = new Connection(port, hostName);
        System.out.println((currentPC + 1) + ". computer: ");
        connection.sendDataLineToComputer(line);
        connection.readResponseFromComputer();
    }

    public void setPortsAndHostNames(String[][] portsAndHostNames) {
        this.portsAndHostNames = portsAndHostNames;
    }

    @Override
    public void run() {
        try {
            startDistributedSending();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

