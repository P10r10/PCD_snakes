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

    @Override
    public void run() {
        doInitialPositioning();
        //System.err.println("initial size:" + cells.size());
        try {
            cells.getLast().request(this);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //TODO: automatic movement
//        move(cells.getLast());
        try {
            System.out.println("HERE");
            sleep(2000); // DEBUG: to delete
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        BoardPosition dest = cells.getFirst().getPosition().getCellRight();
        cells.getFirst().release();
        cells.removeFirst();
        Cell destCell = getBoard().getCell(dest);
        cells.add(destCell);
        try {
            cells.getFirst().request(this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
