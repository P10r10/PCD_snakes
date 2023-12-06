package remote;

import game.Test;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private Socket connection;
    private ObjectInputStream in;
    private Scanner out;

    private InetAddress serverName;
    private int port;

    public Client(InetAddress byName, int port) {
        this.serverName = byName;
        this.port = port;
    }

    public void runClient() {
        try {
            connection = new Socket(serverName, port); // Connect to server
            getStreams(); // Get i/o streams - required to communicate
            processConnection(); // Process connection
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection(); // Close connection
        }
    }

    private void getStreams() throws IOException {
        in = new ObjectInputStream(connection.getInputStream()); // Output - write
//        in = new Scanner(connection.getInputStream()); // Input - read
    }

    private void processConnection() throws IOException {
        try {
            Test test = (Test) in.readObject();
            System.out.println(test);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        String msg = "Hello ";
//        for (int i = 0; i < 5; i++) {
//            out.println(msg + i); // Write
//            System.out.println("[Write] " + msg + i);
//            // Leitura do eco
//            System.out.println(in.nextLine()); // Waits
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        out.println("END");
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

    public static void main(String[] args) throws UnknownHostException {
//        RemoteBoard remoteBoard = new RemoteBoard();
//        SnakeGui remoteGame = new SnakeGui(remoteBoard, 600, 0);
//        remoteGame.init();
        new Client(InetAddress.getByName("localhost"), 1973).runClient();
    }
}
