package game;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import environment.LocalBoard;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

public class AutomaticSnake extends Snake {
    public AutomaticSnake(int id, LocalBoard board) {
        super(id, board);
    }

    private boolean toReposition = false;

    private Cell pickCandidateCell() {
        Cell toReturn = null;
        List<BoardPosition> neighbourPos = getBoard().getNeighboringPositions(cells.getFirst()); // head neighbours
        List<Cell> candidateCells = new LinkedList<>();

        for (BoardPosition bp : neighbourPos) {
            Snake snakeAtPos = getBoard().getCell(bp).getOcuppyingSnake(); // snake at bp
            if (!this.equals(snakeAtPos)) { // if not snake herself
                candidateCells.add(getBoard().getCell(bp));
            }
        }
        if (toReposition) { // if movement to unlock snake is required we chose a random valid cell
            if (!candidateCells.isEmpty()) {
                toReturn = candidateCells.get(new Random().nextInt(candidateCells.size()));
                toReposition = false;
            }
        } else { // regular snake movement towards goal
            // distance between snake's head and goal
            double distToGoal = cells.getFirst().getPosition().distanceTo(getBoard().getGoalPosition());
            for (Cell cell : candidateCells) {
                double candidateDist = cell.getPosition().distanceTo(getBoard().getGoalPosition());
                if (candidateDist < distToGoal || toReturn == null) { // if cell is still null, it gets picked anyway
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
        try {
            Thread.sleep(Board.DELAY); // makes every AutoSnake wait
            getBoard().setStarted();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (!interrupted()) {
            if (getBoard().isFinished()) {
                break; // game over
            }
            try {
                Thread.sleep(Board.PLAYER_PLAY_INTERVAL);
                Cell nextCell = pickCandidateCell();
                if (nextCell != null) {
                    move(nextCell);
                } else { // if null, movement is impossible
                    break;
                }
            } catch (InterruptedException e) {
                toReposition = true;
            }
        }
    }
}
