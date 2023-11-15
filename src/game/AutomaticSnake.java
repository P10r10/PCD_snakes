package game;

import java.util.LinkedList;
import java.util.List;
import javax.swing.text.Position;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

public class AutomaticSnake extends Snake {
    public AutomaticSnake(int id, LocalBoard board) {
        super(id, board);
    }

    private Cell pickCandidateCell() {
        // distance between snake's head and goal
        double distToGoal = cells.getFirst().getPosition().distanceTo(getBoard().getGoalPosition());
        Cell toReturn = null;
        List<BoardPosition> neighbourPos = getBoard().getNeighboringPositions(cells.getFirst());
        for (BoardPosition bp : neighbourPos) { // choose shortest distance to goal
            Snake snakeAtPos = getBoard().getCell(bp).getOcuppyingSnake(); // snake at bp
            if (toReturn == null) { // always chooses at least one
                if (!this.equals(snakeAtPos)) { // if not snake herself
                    toReturn = getBoard().getCell(bp);
                    continue;
                }
            }
            double candidateDist = bp.distanceTo(getBoard().getGoalPosition());
            if (candidateDist < distToGoal && !this.equals(snakeAtPos)) { // if not snake herself
                distToGoal = candidateDist;
                toReturn = getBoard().getCell(bp);
            }
        }
        return toReturn; // returns null if movement is impossible
    }

    @Override
    public void run() {
        doInitialPositioning();
        System.err.println("initial size: " + cells.size());
        try { //DEBUG: review case when snake starts over goal
            while (!interrupted()) {
                Thread.sleep(Board.PLAYER_PLAY_INTERVAL); // reinstate
                Cell nextCell = pickCandidateCell();
                move(nextCell);
            }
        } catch (InterruptedException e) {
            System.out.println(getName() +  " interrupted");
        }
    }
}
