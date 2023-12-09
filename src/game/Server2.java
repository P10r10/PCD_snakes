package game;

import environment.LocalBoard;
import gui.SnakeGui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

    public static void main(String[] args) {
        LocalBoard localBoard = new LocalBoard();
        SnakeGui gui = new SnakeGui(localBoard, 600, 0);
        gui.init();
        try {
            ServerSocket serverSocket = new ServerSocket(1973);
            System.out.println("Server started...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

                // Sending objects to client in a loop
                while (true) {
                    objectOutputStream.writeObject(localBoard);
                    objectOutputStream.reset();

                    // Handling client's text input
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String clientInput = reader.readLine();
                    System.out.println("Client sent: " + clientInput);

                    // Break the loop if the client sends a specific message (e.g., "exit")
                    if (clientInput.equalsIgnoreCase("exit")) {
                        break;
                    }
                }

//                objectOutputStream.close();
//                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
