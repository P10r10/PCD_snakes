package game;

import environment.Cell;
import environment.LocalBoard;

import java.io.Serializable;

public class ObstacleMover extends Thread implements Serializable {
    private Obstacle obstacle;
    private LocalBoard board;

    public ObstacleMover(Obstacle obstacle, LocalBoard board) {
        super();
        this.obstacle = obstacle;
        this.board = board;
    }

    @Override
    public void run() {
        while (!board.isStarted()) {
            try { // waits for the game to start before starting obstacles
                sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        while (obstacle.getRemainingMoves() > 0) {
            try {
                Thread.sleep(Obstacle.OBSTACLE_MOVE_INTERVAL);
                Cell cell = board.getObstacleCell(obstacle);
                cell.removeObstacle();
                board.addGameElement(obstacle); // places Obstacle in new Cell
                obstacle.decreaseMoves();
                board.setChanged();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
