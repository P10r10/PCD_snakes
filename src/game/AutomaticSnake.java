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
            try { //DEBUG: review case when snake starts over goal
                while (!interrupted()) {
                    Thread.sleep(Board.PLAYER_PLAY_INTERVAL);
                    Cell nextCell = pickCandidateCell();
                    if (nextCell.getGameElement() instanceof Obstacle ) {
                        wait(); // HERE review and DRAW SCHEMATICS
                    }
                    if (nextCell == null) { // no more legal movements are possible
                        interrupt();
                        System.out.println(this.getName() + " interrupted!");
                        break;
                    }
                    move(nextCell);
                    if (nextCell.isOcupiedByGoal()) {
                        if (getBoard().getGoal().getValue() == 9) { // game over!
                            getBoard().stopSnakes();
                            System.out.println("Game over!");
                            break;
                        }
                        nextCell.removeGoal();
                        size += getBoard().getGoal().captureGoal();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
    }
}
