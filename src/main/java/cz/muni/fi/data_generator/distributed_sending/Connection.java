package cz.muni.fi.data_generator.distributed_sending;


import cz.muni.fi.data_generator.generator.DataLine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class for establishing connection with another servers.
 */
public class Connection {

    ObjectInputStream socketInput;
    ObjectOutputStream socketOutput;
    Socket socket;

    public Connection (int port, String hostName) {
        try {
            socket = new Socket(hostName, port);
        } catch (Exception e) {
            System.out.println("Error connecting to server:" + e);
        }
    }


    public void sendDataLineToComputer(DataLine dataLine) {
        try {
            if (socketInput == null) {
                socketInput = new ObjectInputStream(socket.getInputStream());
                socketOutput = new ObjectOutputStream(socket.getOutputStream());
            }
        } catch (IOException e) {
            System.out.println("Exception creating new Input/output Streams: " + e);
            return;
        }
        try {
            socketOutput.writeObject(dataLine);
            socketOutput.flush();
        }
        catch(IOException e) {
            System.out.println("Error writting to the socket: " + e);
            return;
        }
    }

    public void readResponseFromComputer() {
        String response;
        try {
            response = (String) socketInput.readObject();
            System.out.println(response);
        }
        catch(Exception e) {
            System.out.println("Problem reading back from server: " + e);
        }
    }

    public void closeSources() {
        try{
            socketInput.close();
            socketOutput.close();
        }
        catch(Exception e) {
            System.out.println("Couldn't close sources " + e);
        }
    }
}

