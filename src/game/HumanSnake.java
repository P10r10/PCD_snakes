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
            if (getBoard().isFinished()) {
                break; // game over
            }
            try {
//                Thread.sleep(Board.PLAYER_PLAY_INTERVAL);
                Thread.sleep(250); // remove
//                List<BoardPosition> neighbourPos = getBoard().getNeighboringPositions(cells.getFirst()); // head neighbours
                BoardPosition headPos = cells.getFirst().getPosition();
//                System.out.println(getBoard().getLastKeyPressed());
                int keyPressed = getBoard().getLastKeyPressed();
                Cell nextCell = null;
                switch (keyPressed) {
                    case 37:
                        nextCell = getBoard().getCell(headPos.getCellLeft());
                        break;
                    case 38:
                        nextCell = getBoard().getCell(headPos.getCellAbove());
                        break;
                    case 39:
                        nextCell = getBoard().getCell(headPos.getCellRight());
                        break;
                    case 40:
                        nextCell = getBoard().getCell(headPos.getCellBelow());
                        break;
                }
                if (nextCell != null) {
                    move(nextCell);
                } else { // if null, movement is impossible
                    break;
                }
            } catch (InterruptedException e) {
                System.out.println("DEB: interrupted run " + getName());
            }
        }
    }

}
