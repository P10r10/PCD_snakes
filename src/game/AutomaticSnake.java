package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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

    private boolean toReposition = false;

    private Cell pickCandidateCell() { // review movement to solve deadlocks
        // distance between snake's head and goal
        double distToGoal = cells.getFirst().getPosition().distanceTo(getBoard().getGoalPosition());
        Cell toReturn = null;
        List<BoardPosition> neighbourPos = getBoard().getNeighboringPositions(cells.getFirst()); // head neighbours
        List<Cell> candidateCells = new ArrayList<>();

        for (BoardPosition bp : neighbourPos) {
            Snake snakeAtPos = getBoard().getCell(bp).getOcuppyingSnake(); // snake at bp
            if (!this.equals(snakeAtPos)) { // if not snake herself
                candidateCells.add(getBoard().getCell(bp));
            }
        }
        if (toReposition) { // if movement to unlock snake is required we chose a random valid cell
            toReturn = candidateCells.get(new Random().nextInt(candidateCells.size()));
            toReposition = false;
        } else { // regular snake movement towards goal
            for (Cell cell : candidateCells) {
                double candidateDist = cell.getPosition().distanceTo(getBoard().getGoalPosition());
                if (candidateDist < distToGoal) {
                    distToGoal = candidateDist;
                    toReturn = cell;
                }
            }
        }
        return toReturn; // returns null if movement is impossible
    }

    @Override
    public void run() {
        doInitialPositioning();
        System.err.println("initial size: " + cells.size());
        while (!interrupted()) {
            if (getBoard().isFinished()) {
                break; // game over
            }
            try { //DEBUG: review case when snake starts over goal
                Thread.sleep(Board.PLAYER_PLAY_INTERVAL);
                Cell nextCell = pickCandidateCell();
                if (nextCell != null) { // if null, movement is impossible
                    move(nextCell);
                }
            } catch (InterruptedException e) {
                toReposition = true;
            }
        }
    }
}
