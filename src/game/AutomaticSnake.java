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
        System.err.println("initial size:" + cells.size());
        try {
            cells.getLast().request(this);
            System.out.println("MOVE -> " + getName()); // DEBUG: to delete
            move();// DEBUG: to delete
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //TODO: automatic movement
//        LinkedList<Cell> xxx = this.getCells();// DEBUG: to delete
//        for (Cell x : xxx) {// DEBUG: to delete
//            System.out.println(x.getPosition());// DEBUG: to delete
//        }// DEBUG: to delete
        //sleep(Board.PLAYER_PLAY_INTERVAL); // DEBUG: to delete
    }


}
