package remote;

import environment.Board;
import game.Teste;
import gui.SnakeGui;

import java.io.*;
import java.net.Socket;


public class Client3 {

    public static void main(String[] args) {
        RemoteBoard remoteBoard = new RemoteBoard();
        SnakeGui gui = new SnakeGui(remoteBoard, 600, 0);
        gui.init();
        try {
            Socket socket = new Socket("127.0.0.1", 1973);

            // Receiving objects from server
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            // Continuously receive objects and send text
            while (true) {
                try {
                    Board localBoard = (Board) objectInputStream.readObject();
                    System.out.println("Received from server: " + localBoard); // remove
                    remoteBoard.setCells(localBoard.getCells());
                    remoteBoard.setChanged();
                    // Sending text to server
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
//
//                    System.out.print("Enter text to send to server (type 'exit' to quit): ");
//                    String text = reader.readLine();
//                    writer.println(text);
//
//                    if (text.equalsIgnoreCase("exit")) {
//                        break;
//                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
//            objectInputStream.close();
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

