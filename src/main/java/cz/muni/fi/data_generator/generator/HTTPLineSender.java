package cz.muni.fi.data_generator.generator;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lucka on 31.3.2015.
 */
public class HTTPLineSender implements LineSender{
    private URL url;
    private HttpURLConnection connection;
    private ObjectOutputStream objectOutputStream;

    public HTTPLineSender(URL url) throws IOException, ConnectException {
        this.url = url;
        this.connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true); //this is to enable writing
        objectOutputStream = new ObjectOutputStream(connection.getOutputStream());
    }


    @Override
    public void send(DataLine dataLine) {
        try {
            objectOutputStream.writeObject(dataLine);
        } catch (IOException e) {
            System.out.println("Problem with sending data");
        }
    }

    public void close() throws IOException {
        objectOutputStream.close();
    }
}
