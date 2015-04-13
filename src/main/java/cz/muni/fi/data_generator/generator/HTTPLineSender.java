package cz.muni.fi.data_generator.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by lucka on 31.3.2015.
 */
public class HTTPLineSender implements LineSender{
    private URL url;
    private HttpURLConnection connection;
    private ObjectOutputStream objectOutputStream;

    public HTTPLineSender(URL url) throws IOException {
        this.url = url;
        refresh();
    }


    @Override
    public void send(DataLine dataLine) {
        try {
            objectOutputStream.writeObject(dataLine.toString());
            objectOutputStream.flush();
            read();
            refresh();
        } catch (IOException e) {
            System.out.println("Problem with sending data");
        }
    }

    public void refresh() throws IOException {
        this.connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true); //this is to enable writing
        connection.setRequestMethod("POST");
        objectOutputStream = new ObjectOutputStream(connection.getOutputStream());
    }

    public void read() throws IOException {
        new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }

    public void close() throws IOException {
        objectOutputStream.close();
    }
}
