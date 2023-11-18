package game;

import environment.Board;
import environment.LocalBoard;

public class Goal extends GameElement {
    private int value = 1;
    private final Board board;
    public static final int MAX_VALUE = 10;

    public Goal(Board board) {
        this.board = board;
    }

    public int getValue() {
        return value;
    }

    private void incrementValue() {
        if (value < MAX_VALUE) {
            value++;
        }
    }

    public int captureGoal() {
        incrementValue();
        if (value < MAX_VALUE) {
            board.addGameElement(this);
        } else {
            System.out.println("Game over!");
        }
        return value;
    }
}
