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

/**
 * Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Server.
 * Only for part II of the project.
 *
 * @author luismota
 */
public class RemoteBoard extends Board {
    private int lastKeyPressed = 39; // defaults to moving right

    @Override
    public int getLastKeyPressed() {
        return lastKeyPressed;
    }

    @Override
    public void handleKeyPress(int keyCode) {
//        System.out.println("pressed " + keyCode);
        lastKeyPressed = keyCode;
    }

    @Override
    public void handleKeyRelease() {
        // TODO
    }

    @Override
    public void init() {
//        HumanSnake hs = new HumanSnake(1313, this);
//        hs.start();
        // TODO
    }

    @Override
    public void stopSnakes() {
        // TODO
    }
}
