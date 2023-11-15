package environment;

import java.io.Serializable;

import javax.sound.midi.SysexMessage;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.Snake;
import game.AutomaticSnake;

/**
 * Main class for game representation.
 *
 * @author luismota
 */
public class Cell {
    private BoardPosition position;
    private Snake ocuppyingSnake = null;
    private GameElement gameElement = null;

    public GameElement getGameElement() {
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
            System.out.println(snake.getName() + " waiting ...");
            wait();
        }
        ocuppyingSnake = snake;
        notifyAll();
    }

    public synchronized void release() throws InterruptedException {
        while (ocuppyingSnake == null) { // add obstacle after
            wait();
        }
        ocuppyingSnake = null;
        notifyAll();
    }

    public boolean isOcupiedBySnake() {
        return ocuppyingSnake != null;
    }

    public synchronized void setGameElement(GameElement element) {
        gameElement = element;
    }

    public boolean isOcupied() {
        return isOcupiedBySnake() || (gameElement != null && gameElement instanceof Obstacle);
    }

    public Snake getOcuppyingSnake() {
        return ocuppyingSnake;
    }

    public void removeGoal() {
        gameElement = null;
    }

    public void removeObstacle() {
        //TODO
    }

    public Goal getGoal() {
        return (Goal) gameElement;
    }

    public boolean isOcupiedByGoal() {
        return (gameElement != null && gameElement instanceof Goal);
    }
}
