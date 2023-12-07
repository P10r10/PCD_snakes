package gui;

import java.io.Console;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;

import javax.net.ssl.StandardConstants;

import environment.LocalBoard;
import game.Server;
import remote.Client;
import remote.RemoteBoard;

public class Main {
    public static void main(String[] args) {
        LocalBoard board = new LocalBoard();
        SnakeGui game = new SnakeGui(board, 600, 0);
        game.init();
        new Server(board).runServer();
    }
}
