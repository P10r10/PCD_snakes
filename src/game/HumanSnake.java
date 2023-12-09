package game;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;

import java.util.List;

/**
 * Class for a remote snake, controlled by a human
 *
 * @author luismota
 */
public class HumanSnake extends Snake { // removed abstract

    public HumanSnake(int id, Board board) {
        super(id, board);
    }

    @Override
    public void run() {
        System.out.println("Human snake " + getName() + " started.");
        doInitialPositioning();
        while (!interrupted()) {
//            if (getBoard().isFinished()) { // reinstate?
//                break; // game over
//            }
            try {
                Thread.sleep(Board.PLAYER_PLAY_INTERVAL); // reinstate
                BoardPosition headPos = cells.getFirst().getPosition();
                int keyPressed = getBoard().getLastKeyPressed();
                Cell nextCell = null;
                BoardPosition nextBP = null;
                switch (keyPressed) {
                    case 37:
                        nextBP = headPos.getCellLeft();
                        break;
                    case 38:
                        nextBP = headPos.getCellAbove();
                        break;
                    case 39:
                        nextBP = headPos.getCellRight();
                        break;
                    case 40:
                        nextBP = headPos.getCellBelow();
                        break;
                }
                if (getBoard().getCell(nextBP) != null && !getBoard().getCell(nextBP).isOccupied()) {
                    nextCell = getBoard().getCell(nextBP);
                }
                if (nextCell != null) {
                    move(nextCell);
                }
            } catch (InterruptedException e) {
                System.out.println("DEB: interrupted run @ HumanSnake " + getName()); // REMOVE
            }
        }
    }
}
