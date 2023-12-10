package game;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;

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
        doInitialPositioning();
        while (!getBoard().isStarted()) {
            try { // waits for the game to start before starting HumanSnake
                sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        while (!interrupted()) {
            if (getBoard().isFinished()) {
                break; // game over
            }
            try {
                Thread.sleep(Board.PLAYER_PLAY_INTERVAL); // reinstate
                BoardPosition headPos = cells.getFirst().getPosition();
                int keyPressed = getBoard().getLastKeyPressed();
                Cell nextCell = null;
                BoardPosition nextBP = switch (keyPressed) {
                    case 37 -> headPos.getCellLeft();
                    case 38 -> headPos.getCellAbove();
                    case 39 -> headPos.getCellRight();
                    case 40 -> headPos.getCellBelow();
                    default -> null;
                };
                if (getBoard().getCell(nextBP) != null && !getBoard().getCell(nextBP).isOccupied()) {
                    nextCell = getBoard().getCell(nextBP);
                }
                if (nextCell != null) {
                    move(nextCell);
                }
            } catch (InterruptedException e) {
                //do nothing
            }
        }
    }
}
