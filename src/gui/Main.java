package gui;

import java.io.Console;
import java.io.IOException;
import java.util.Random;

import javax.net.ssl.StandardConstants;

import environment.LocalBoard;
import game.Server;

public class Main {
    public static void main(String[] args) {
        LocalBoard board = new LocalBoard();
        SnakeGui game = new SnakeGui(board, 600, 0);
        game.init();
        // Launch server
        // TODO
//         Problems to address:
//           -implement obstacles HERE
//            - Obstacle placed over snake position
//           -prevent deadlocks
//           - (..)
    }
}
