package remote;

import gui.SnakeGui;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client2 {
    public static void main(String[] args) throws UnknownHostException {
        RemoteBoard remoteBoard = new RemoteBoard();
        SnakeGui gui = new SnakeGui(remoteBoard, 600, 0);
        gui.init();
        new Client(remoteBoard, InetAddress.getByName("localhost"), 1973).runClient();
    }
}
