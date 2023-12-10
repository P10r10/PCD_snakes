package game;

import environment.Board;

import java.io.Serializable;

public class Obstacle extends GameElement implements Serializable {


    private static final int NUM_MOVES = 3;
    public static final int OBSTACLE_MOVE_INTERVAL = 1200;
    private int remainingMoves = NUM_MOVES;
    private final Board board;

    public Obstacle(Board board) {
        super();
        this.board = board;
    }

    public int getRemainingMoves() {
        return remainingMoves;
    }

    public void decreaseMoves() {
        remainingMoves--;
    }

}
