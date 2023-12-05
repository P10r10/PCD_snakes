package gui;

import java.io.Console;
import java.io.IOException;
import java.util.Random;

import javax.net.ssl.StandardConstants;

import environment.LocalBoard;
import game.Server;
import remote.RemoteBoard;

public class Main {
    public static void main(String[] args) {
//        LocalBoard board = new LocalBoard();
//        SnakeGui game = new SnakeGui(board, 600, 0); // reinstate
        RemoteBoard remoteBoard = new RemoteBoard();
        SnakeGui game = new SnakeGui(remoteBoard, 600, 0);

        game.init();
        new Server().runServer();
    }
}
