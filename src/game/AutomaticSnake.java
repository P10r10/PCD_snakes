package game;

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

    private Cell pickCandidateCell() {
        // distance between snake's head and goal
        Double distToGoal = cells.getFirst().getPosition().distanceTo(getBoard().getGoalPosition());
        Cell toReturn = null;
        List<BoardPosition> neighbourPos = getBoard().getNeighboringPositions(cells.getFirst());
        for (BoardPosition bp : neighbourPos) { // choose shortest distance to goal
            Double candidate = bp.distanceTo(getBoard().getGoalPosition());
            if (candidate < distToGoal) {
                distToGoal = candidate;
                toReturn = getBoard().getCell(bp);
            }
        }
        return toReturn;
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

//        while (true) {
//            try {
//                Thread.sleep(Board.PLAYER_PLAY_INTERVAL);
//                List<BoardPosition> neighbourPos = getBoard().getNeighboringPositions(this.cells.getFirst());
//                Cell nextCell;
//                do { // next position is random // MODIFY towards target after
//                    BoardPosition nextPos = neighbourPos.get(new Random().nextInt(neighbourPos.size()));
//                    nextCell = getBoard().getCell(nextPos);
//                } while (nextCell.isOcupiedBySnake());
//                move(nextCell);
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }

        while (true) {
            try {
                Thread.sleep(Board.PLAYER_PLAY_INTERVAL);
                Cell nextCell;
                do { // moves towards target
                    nextCell = pickCandidateCell();
                } while (nextCell.isOcupiedBySnake());
                move(nextCell);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }



    }
}
