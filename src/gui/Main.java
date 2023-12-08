package gui;

import environment.LocalBoard;
import game.Server;

public class Main {
    public static void main(String[] args) {
        LocalBoard board = new LocalBoard();
        System.out.println(board);
        SnakeGui game = new SnakeGui(board, 600, 0);
        game.init();
        new Server(board).runServer();
    }
}
