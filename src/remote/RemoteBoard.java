package remote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

import environment.LocalBoard;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import game.Goal;
import game.HumanSnake;
import game.Obstacle;
import game.Snake;
import gui.SnakeGui;

/**
 * Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Server.
 * Only for part II of the project.
 *
 * @author luismota
 */
public class RemoteBoard extends Board {
    private int lastKeyPressed = 39; // defaults to moving right
//    private static int nbSnakes = 0;

//    private HumanSnake humanSnake;

    public RemoteBoard() {
//        nbSnakes++;
//        humanSnake = new HumanSnake(nbSnakes, this);
    }

//    public HumanSnake getHumanSnake() {
//        return humanSnake;
//    }

    @Override
    public int getLastKeyPressed() {
        return lastKeyPressed;
    }

    @Override
    public void handleKeyPress(int keyCode) {
        System.out.println("pressed " + keyCode);
        lastKeyPressed = keyCode;
    }

    @Override
    public void handleKeyRelease() {
        // TODO
    }

    @Override
    public void init() {
        System.out.println("init");
        System.out.println(getSnakes().size());
//        nbSnakes++;

//        HumanSnake hs = new HumanSnake(1313, this);
//        hs.start();
//        getSnakes().getFirst().start();
        // TODO

    }

    @Override
    public void stopSnakes() {
        // TODO
    }
}
