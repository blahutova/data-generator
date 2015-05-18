package cz.muni.fi.data_generator.distributed_sending;//The server code Server.java:

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Class which represents server in distributed sending.
 *
 */
public class Server {
    private ServerSocket serverSocket;

    public Server(int port) {
        try
        {
            serverSocket = new ServerSocket(port);
            createThreadForClients();
        }
        catch (IOException e) {
            System.out.println("Exception on new ServerSocket: " + e);
        }
    }

    public void createThreadForClients() throws IOException {
            TcpThread t = new TcpThread(serverSocket);
            t.start();
    }
}

