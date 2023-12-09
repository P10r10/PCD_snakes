package remote;

import environment.Board;
import environment.Cell;
import game.HumanSnake;
import game.Snake;
import gui.SnakeGui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Scanner;

public class Client {
    private Socket connection;
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
        in = new ObjectInputStream(connection.getInputStream()); // Input - read
        out = new PrintWriter(connection.getOutputStream(), true); // Output - write
    }

    private void processConnection() throws IOException {

        while (true) {
            try {
                Object receivedObj = in.readObject();
                if (receivedObj instanceof Cell[][] cells) {
                    remoteBoard.setCells(cells);
                } else if (receivedObj instanceof LinkedList) {
                    LinkedList<Snake> snakes = (LinkedList<Snake>) receivedObj;
//                    Board board = snakes.getFirst().getBoard();
//                    System.out.println(board);
//                    remoteBoard.addSnake(new HumanSnake(1234, board));
                    remoteBoard.setSnakes(snakes);
                }
                System.out.println("D: " + remoteBoard.getLastKeyPressed());
                out.println(remoteBoard.getLastKeyPressed());
                remoteBoard.setChanged();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

//        REMOVE COMMENTED CODE BELLOW (TEACHER)
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
        RemoteBoard remoteBoard = new RemoteBoard();
        SnakeGui remoteGame = new SnakeGui(remoteBoard, 600, 0);
        remoteGame.init();
        new Client(remoteBoard, InetAddress.getByName("localhost"), 1973).runClient();

    }
}
