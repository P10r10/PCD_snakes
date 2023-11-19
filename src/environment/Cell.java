package environment;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.Snake;

/**
 * Main class for game representation.
 *
 * @author luismota
 */
public class Cell {
    private BoardPosition position;
    private Snake ocuppyingSnake = null;
    private GameElement gameElement = null;

    public synchronized GameElement getGameElement() {
        return gameElement;
    }

    public Cell(BoardPosition position) {
        super();
        this.position = position;
    }

    public BoardPosition getPosition() {
        return position;
    }

    public synchronized void request(Snake snake) throws InterruptedException { // consider locks?
        while (ocuppyingSnake != null || gameElement != null) { // cell is occupied
            if (gameElement instanceof Goal) { // when snake reaches goal, she captures
                snake.addSize(getGoal().captureGoal());
                removeGoal();
                break; // goals won't block snake
            }
            wait();
        }
        ocuppyingSnake = snake;
        notifyAll();
    }

    public synchronized void release() {
        ocuppyingSnake = null;
        notifyAll();
    }

    public boolean isOccupiedBySnake() {
        return ocuppyingSnake != null;
    }

    public synchronized void setGameElement(GameElement element) {
        gameElement = element;
    }

    public boolean isOccupied() {
        return isOccupiedBySnake() || (gameElement != null && gameElement instanceof Obstacle);
    }

    public Snake getOcuppyingSnake() {
        return ocuppyingSnake;
    }

    public void removeGoal() {
        if (gameElement instanceof Goal) {
            gameElement = null;
        }
    }

    public synchronized void removeObstacle() {
        if (gameElement instanceof Obstacle) {
            gameElement = null;
            notifyAll();
        }
    }

    public Goal getGoal() {
        return (Goal) gameElement;
    }

    public boolean isOccupiedByGoal() {
        return (gameElement != null && gameElement instanceof Goal);
    }
}
