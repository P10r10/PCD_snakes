package environment;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.*;

/**
 * Class representing the state of a game running locally
 *
 * @author luismota
 */
public class LocalBoard extends Board implements Serializable {

    private static final int NUM_SNAKES = 1;
    private static final int NUM_OBSTACLES = 15; // 15
    private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 3;

    private final LinkedList<ObstacleMover> obstacleMovers = new LinkedList<>();

    public LocalBoard() {
        for (int i = 0; i < NUM_SNAKES; i++) { // add snakes
            AutomaticSnake snake = new AutomaticSnake(i, this);
            snakes.add(snake);
        }
        addObstacles(NUM_OBSTACLES);
        addGoal();
        System.err.println("All elements placed");
    }

    public void init() {
        for (Snake s : snakes) // start all snakes threads
            s.start();
        for (Obstacle obstacle : getObstacles()) { // create obstacle movers
            obstacleMovers.add(new ObstacleMover(obstacle, this));
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(NUM_SIMULTANEOUS_MOVING_OBSTACLES);
        for (ObstacleMover om : obstacleMovers) {
            threadPool.submit(om); // only 3 threads running at the same time
        }
    }

    public void stopSnakes() {
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

    @Override
    public int getLastKeyPressed() {
        return lastKeyPressed; // do nothing... No keys relevant in local game
    }
}
