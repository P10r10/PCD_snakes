package game;

import environment.Board;
import environment.LocalBoard;
import gui.SnakeGui;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket server;
    private Board board;

    public static int hsCount = 0;

    public Server(Board board) {
        this.board = board;
    }

    public void runServer() {
        try {
            server = new ServerSocket(1973); // Create server socket
            while (true) { // Wait for new connection
                waitForConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() throws IOException {
        System.out.println("Server waiting for a new connection...");
        Socket socket = server.accept(); // Waits ...
        ConnectionHandler handler = new ConnectionHandler(socket);
        handler.start();
        System.out.println("[new connection] " + socket.getInetAddress().getHostName());
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
                System.out.println("Closing connection"); // REMOVE
                closeConnection(); // Close connection
            }
        }

        private void getStreams() throws IOException {
            out = new ObjectOutputStream(connection.getOutputStream()); // Output - write
            in = new Scanner(connection.getInputStream()); // Input - read
        }

        private class txtComms extends Thread {
            @Override
            public void run() {
                System.out.println("COMMS RUNNING!");
                int key = in.nextInt();
            }
        }

        private void processConnection() throws IOException { // OBJ
            while (true) {
                try {
                    Thread.sleep(50); // seems to have less impact on OptionalDataException bug
                } catch (InterruptedException e) {
                }
                out.writeObject(board);
                out.reset(); // needed because of cache usage
                //txt
                int key = in.nextInt();
                if (key == 99) { // 99 - code to create HumanSnake
                    hsCount++;
                    HumanSnake humanSnake = new HumanSnake(hsCount, board);
                    board.addSnake(humanSnake);
                    humanSnake.start();
                } else if (key != board.getLastKeyPressed()) {
                    board.setLastKeyPressed(key);
                }
            }
        }

        private void processConnection2() { // TXT

            while (true) {
//                try {
//                    Thread.sleep(Board.REMOTE_REFRESH_INTERVAL);
//                    out.writeObject(board.getCells());
//                    out.reset(); // needed because of cache usage //REMOVE?
//                    out.writeObject(board.getSnakes());
//                    out.reset(); // needed because of cache usage
                int key = in.nextInt();
                if (key == 99) { // 99 - code to create HumanSnake
                    hsCount++;
                    HumanSnake humanSnake = new HumanSnake(hsCount, board);
                    board.addSnake(humanSnake);
                    humanSnake.start();
                } else if (key != board.getLastKeyPressed()) {
                    board.setLastKeyPressed(key);
                }
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            }
        }

        private void closeConnection() {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
                if (connection != null)
                    connection.close();
//                System.exit(0); // REVIEW WITH SEVERAL HUMAN PLAYERS
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        LocalBoard board = new LocalBoard();
        SnakeGui gui = new SnakeGui(board, 600, 0);
        gui.init();
        new Server(board).runServer();
    }
}
