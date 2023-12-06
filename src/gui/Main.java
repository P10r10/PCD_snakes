package gui;

import java.io.Console;
import java.io.IOException;
import java.util.Random;

import javax.net.ssl.StandardConstants;

import environment.LocalBoard;
import game.Server;
import remote.RemoteBoard;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LocalBoard board = new LocalBoard();
        SnakeGui game = new SnakeGui(board, 600, 0);
        //wait here?
//        Thread.sleep(10000); // waiting for human snakes to connect
        game.init();
        new Server(board).runServer();
    }
}
