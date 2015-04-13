package cz.muni.fi.data_generator.distributedSending;

import com.sun.org.apache.xpath.internal.SourceTree;
import cz.muni.fi.data_generator.generator.DataLine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lucka on 8.4.2015.
 */
public class TcpThread extends Thread {
    // the socket where to listen/talk
    Socket socket;
    private ServerSocket serverSocket;
    ObjectInputStream socketInput;
    ObjectOutputStream socketOutput;

    TcpThread(ServerSocket socket) {
        this.serverSocket = socket;
    }

    public void run() {
		while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Failed to create connection" + e);
            }
            createOutputAndInput();
            readDataLineFromClient();
            closeSources();
       }
    }

    public void createOutputAndInput() {

        try {
            socketOutput = new ObjectOutputStream(socket.getOutputStream());
            socketOutput.flush();
            socketInput = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Exception creating new Input/Output Streams: " + e);
            return;
        }
    }

    public void readDataLineFromClient()  {
        DataLine dataLine;
        try {
            dataLine = (DataLine) socketInput.readObject();
            socketOutput.writeObject(dataLine.toString());
            socketOutput.flush();
        } catch (IOException e) {
            System.out.println("Exception reading/writing  Streams: " + e);
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("Sent object wasn't found " + e);
            return;
        }
    }

    public void closeSources() {
        try {
            socketOutput.close();
            socketInput.close();
        } catch (IOException e) {
            System.out.println("Problem with closing files" + e);
        }
    }
}

