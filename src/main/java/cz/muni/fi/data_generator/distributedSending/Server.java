package cz.muni.fi.data_generator.distributedSending;//The server code Server.java:

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private ServerSocket serverSocket;

    public Server(int port) {
        try
        {
            serverSocket = new ServerSocket(port);
            createThreadsForClients();
        }
        catch (IOException e) {
            System.out.println("Exception on new ServerSocket: " + e);
        }
    }

    public void createThreadsForClients() throws IOException {
            TcpThread t = new TcpThread(serverSocket);
            t.start();
    }
}

