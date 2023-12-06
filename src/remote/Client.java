package remote;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import game.GameElement;
import gui.SnakeGui;

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
    RemoteBoard remoteBoard = new RemoteBoard();
    SnakeGui remoteGame = new SnakeGui(remoteBoard, 600, 0);

    public Client(InetAddress byName, int port) {
        this.serverName = byName;
        this.port = port;
    }

    public void runClient() {
        try {
            remoteGame.init(); // Init remote game
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
        Board board = null;

        while (true) { // how to end? !interrupted?
            try {
                board = (Board) in.readObject();
                if (board.isFinished()) {
                    break;
                }
                BoardPosition gp = board.getGoalPosition();
                System.out.println(gp);
                Cell localGoalCell = board.getCell(gp);
                Cell remoteGoalCell = remoteBoard.getCell(gp);

                GameElement ge = localGoalCell.getGameElement();
                remoteGoalCell.setGameElement(ge);
                remoteBoard.setChanged();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

//        REMOVE COMMENTED CODE BELLOW
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
        new Client(InetAddress.getByName("localhost"), 1973).runClient();
    }
}
