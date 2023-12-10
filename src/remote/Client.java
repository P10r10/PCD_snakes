package remote;

import environment.Board;
import environment.Cell;
import game.Snake;
import gui.SnakeGui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

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

    private void processConnection2() { // TXT
        out.println(99); // code to create HumanSnake on Server side
        while (true) {
            out.println(remoteBoard.getLastKeyPressed());
        }
    }

    private void processConnection() { // OBJ
        out.println(99); // code to create HumanSnake on Server side
        while (true) {
            try {
                out.println(remoteBoard.getLastKeyPressed());
                Object receivedObj = in.readObject();
                Board recB = (Board) receivedObj;
                remoteBoard.setCells(recB.getCells());
                remoteBoard.setSnakes(recB.getSnakes());
                remoteBoard.setChanged();
            } catch (ClassNotFoundException | IOException e) {
//                e.printStackTrace(); // OptionalDataException bug
            }
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
