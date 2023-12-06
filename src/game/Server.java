package game;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket server;

    public void runServer() {
        try {
            server = new ServerSocket(1973, 1); // Create server socket
            while (true) { // Wait for new connection
                waitForConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() throws IOException {
        System.out.println("Waiting for a new connection...");
        Socket connection = server.accept(); // Waits ...
        ConnectionHandler handler = new ConnectionHandler(connection);
        handler.start();
        System.out.println("[new connection] " + connection.getInetAddress().getHostName());
    }

    private class ConnectionHandler extends Thread {
        private final Socket connection;
        private ObjectOutputStream out;
        private Scanner in;

        public ConnectionHandler(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                getStreams(); // Get i/o streams - required to communicate
                processConnection(); // Process connection
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeConnection(); // Close connection
            }
        }

        private void getStreams() throws IOException {
            out = new ObjectOutputStream(connection.getOutputStream()); // Output - write
            in = new Scanner(connection.getInputStream()); // Input - read
        }

        private void processConnection() throws IOException { // remove Throws?
            Test alex = new Test();
            System.out.println("Sending ALEX");
            out.writeObject(alex);
//            String msg;
//            do {
//                msg = in.nextLine(); // Waits ...
//                System.out.println("[Read] " + msg);
//                out.println("[Eco] " + msg);
//            } while (!"END".equals(msg));
        }

        private void closeConnection() {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
                if (connection != null)
                    connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
