package game;

import java.io.Serializable;
import java.util.LinkedList;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;

/**
 * Base class for representing Snakes.
 * Will be extended by HumanSnake and AutomaticSnake.
 * Common methods will be defined here.
 *
 * @author luismota
 */
public abstract class Snake extends Thread implements Serializable {
    protected LinkedList<Cell> cells = new LinkedList<Cell>();
    protected int size = 5;
    private int id;
    private Board board;

    public Snake(int id, Board board) {
        this.id = id;
        this.board = board;
    }

    public int getSize() {
        return size;
    }

    public void addSize(int amount) {
        this.size += amount;
    }

    public int getIdentification() {
        return id;
    }

    public int getLength() {
        return cells.size();
    }

    public LinkedList<Cell> getCells() {
        return cells;
    }

    protected void move(Cell cell) throws InterruptedException { // moves to Cell cell
        cell.request(this);
        cells.addFirst(cell);
        if (cells.size() == size) { // stops snake growth when size is met
            cells.removeLast().release();
        }
        board.setChanged();
    }

    public LinkedList<BoardPosition> getPath() {
        LinkedList<BoardPosition> coordinates = new LinkedList<BoardPosition>();
        for (Cell cell : cells) {
            coordinates.add(cell.getPosition());
        }
        return coordinates;
    }

    protected void doInitialPositioning() {  // At startup, snake occupies a single cell
        int posX = 0;
        int posY = (int) (Math.random() * Board.NUM_ROWS); // Random position on the first column.
        BoardPosition bp = new BoardPosition(posX, posY);
        try {
            board.getCell(bp).request(this); // add snake reference to Cell
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            System.out.println("Exception in doInitialPositioning"); // REMOVE
            e1.printStackTrace();
        }
        cells.add(board.getCell(bp)); // add Cell to this Snake cells (LinkedList)
        System.err.println("Snake " + getIdentification() + " starting at: " + getCells().getLast().getPosition());
    }
    public Board getBoard() {
        return board;
    }
}
