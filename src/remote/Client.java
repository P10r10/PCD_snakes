package remote;

import environment.Board;
import gui.SnakeGui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket socket;
    private ObjectInputStream in;
    private PrintWriter out;
    private InetAddress serverName;
    private int port;
    private Board remoteBoard;

    public Client(Board remoteBoard, InetAddress byName, int port) {
        this.remoteBoard = remoteBoard;
        this.serverName = byName;
        this.port = port;
    }

    public void runClient() {
        try {
            socket = new Socket(serverName, port); // Connect to server
            getStreams(); // Get i/o streams - required to communicate
            processConnection(); // Process connection
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection(); // Close connection
        }
    }

    private void getStreams() throws IOException {
        in = new ObjectInputStream(socket.getInputStream()); // Input - read
        out = new PrintWriter(socket.getOutputStream(), true); // Output - write
    }

    private void processConnection() {
            out.println(99); // code to create HumanSnake on Server side
            while (true) {
//                try {
//                    Object receivedObj = in.readObject();
//                    if (receivedObj instanceof Cell[][] cells) {
//                        remoteBoard.setCells(cells);
//                    } else if (receivedObj instanceof LinkedList) {
//                        LinkedList<Snake> snakes = (LinkedList<Snake>) receivedObj;
//                        remoteBoard.setSnakes(snakes);
//                    }
//                    //                System.out.println("D: " + remoteBoard.getLastKeyPressed());
//                    //                out.println(remoteBoard.getLastKeyPressed());
//                    remoteBoard.setChanged();
//                } catch (ClassNotFoundException | IOException e) {
//                    System.out.println("X");
//                    throw new RuntimeException(e);
//                }
//            }
            out.println(remoteBoard.getLastKeyPressed());
        }
    }

    private void closeConnection() {
        try {
            if (out != null)
                out.close();
            if (in != null)
                in.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        RemoteBoard remoteBoard = new RemoteBoard();
        SnakeGui gui = new SnakeGui(remoteBoard, 600, 0);
        gui.init();
        new Client(remoteBoard, InetAddress.getByName("localhost"), 1973).runClient();
    }
}
