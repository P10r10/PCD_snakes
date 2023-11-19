package environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.*;

/**
 * Class representing the state of a game running locally
 *
 * @author luismota
 */
public class LocalBoard extends Board {

    private static final int NUM_SNAKES = 0; // default = 2
    private static final int NUM_OBSTACLES = 3; // default = 25
    private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 3;

    private final LinkedList<ObstacleMover> obstacleMovers = new LinkedList<>();

    public LocalBoard() {

        for (int i = 0; i < NUM_SNAKES; i++) {
            AutomaticSnake snake = new AutomaticSnake(i, this);
            snakes.add(snake);
        }
        addObstacles(NUM_OBSTACLES);
        addGoal();
        System.err.println("All elements placed");
    }

    public void init() {
        // TODO: launch other threads
        for (Snake s : snakes) // start all snakes threads
            s.start();
        for (Obstacle obstacle : getObstacles()) { // create obstacle movers
            obstacleMovers.add(new ObstacleMover(obstacle, this));
        }
        for (ObstacleMover om : obstacleMovers) {
            om.start();
        }
    }

    public void stopSnakes() { // stop other threads?
        for (Snake s : snakes) {
            s.interrupt();
        }
    }

    @Override
    public void handleKeyPress(int keyCode) {
        // do nothing... No keys relevant in local game
    }

    @Override
    public void handleKeyRelease() {
        // do nothing... No keys relevant in local game
    }
}
