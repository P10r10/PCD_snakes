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
            if (toReturn == null) { // always chooses at least one
                if (!getBoard().getCell(bp).isOcupiedBySnake()) {
                    toReturn = getBoard().getCell(bp);
                    continue;
                }
            }
            double candidate = bp.distanceTo(getBoard().getGoalPosition());
            Cell candidateCell = getBoard().getCell(bp);
            if (candidate < distToGoal && !candidateCell.isOcupiedBySnake()) {
                distToGoal = candidate;
                toReturn = getBoard().getCell(bp);
            }
        }
        return toReturn; // returns null if movement is impossible
    }

    @Override
    public void run() {
        doInitialPositioning();
        System.err.println("initial size: " + cells.size());
        try {
            cells.getLast().request(this);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //TODO: automatic movement
        while (true) {
            try { //DEBUG: review case when snake starts at goal

                while (true) {
                    Thread.sleep(Board.PLAYER_PLAY_INTERVAL);
                    Cell nextCell = pickCandidateCell();
                    if (nextCell == null) {
                        System.out.println("IMPOSSIBLE");
                        // HERE review
                    }
                    move(nextCell);
                    if (nextCell.isOcupiedByGoal()) {
                        nextCell.removeGoal();
                        size += getBoard().getGoal().captureGoal();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
