package environment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import game.*;

public abstract class Board extends Observable implements Serializable { // REMOVE SERIALIZABLE?
    protected Cell[][] cells;
    private BoardPosition goalPosition;
    public static final long PLAYER_PLAY_INTERVAL = 100;
    public static final long REMOTE_REFRESH_INTERVAL = 200;
    public static final int NUM_COLUMNS = 30;
    public static final int NUM_ROWS = 30;
    protected LinkedList<Snake> snakes = new LinkedList<Snake>();
    private final LinkedList<Obstacle> obstacles = new LinkedList<Obstacle>();

    private final LinkedList<Cell> cellsWithObstacles = new LinkedList<>();

    protected boolean finished = false;


    public Board() {
        cells = new Cell[NUM_COLUMNS][NUM_ROWS];
        for (int x = 0; x < NUM_COLUMNS; x++) {
            for (int y = 0; y < NUM_ROWS; y++) {
                cells[x][y] = new Cell(new BoardPosition(x, y));
            }
        }
    }

    public void setFinished() {
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    public Cell getCell(BoardPosition bp) { // next test avoids out of bounds
        if (bp != null && bp.x < NUM_ROWS && bp.y < NUM_COLUMNS &&
                bp.x >= 0 && bp.y >= 0) {
            return cells[bp.x][bp.y];
        }
        return null;
    }

    protected BoardPosition getRandomPosition() {
        return new BoardPosition((int) (Math.random() * NUM_ROWS), (int) (Math.random() * NUM_ROWS));
    }

    public BoardPosition getGoalPosition() {
        return goalPosition;
    }

    public void setGoalPosition(BoardPosition goalPosition) {
        this.goalPosition = goalPosition;
    }

    public synchronized void addGameElement(GameElement gameElement) {
        boolean placed = false;
        while (!placed) {
            BoardPosition pos = getRandomPosition();
            if (!getCell(pos).isOccupied() && !getCell(pos).isOccupiedByGoal()) {
                getCell(pos).setGameElement(gameElement);
                if (gameElement instanceof Obstacle) {
                    Cell cell = getCell(pos);
                    cellsWithObstacles.add(cell);
                }
                if (gameElement instanceof Goal) {
                    setGoalPosition(pos);
                    System.out.println("Goal placed at: " + pos);
                }
                placed = true;
            }
        }
    }

    public List<BoardPosition> getNeighboringPositions(Cell cell) {
        ArrayList<BoardPosition> possibleCells = new ArrayList<BoardPosition>();
        BoardPosition pos = cell.getPosition();
        if (pos.x > 0)
            possibleCells.add(pos.getCellLeft());
        if (pos.x < NUM_COLUMNS - 1)
            possibleCells.add(pos.getCellRight());
        if (pos.y > 0)
            possibleCells.add(pos.getCellAbove());
        if (pos.y < NUM_ROWS - 1)
            possibleCells.add(pos.getCellBelow());
        return possibleCells;
    }

    protected void addGoal() {
        addGameElement(new Goal(this));
    }

    protected void addObstacles(int numberObstacles) {
        getObstacles().clear(); // necessary when resetting obstacles
        while (numberObstacles > 0) {
            Obstacle obs = new Obstacle(this);
            addGameElement(obs);
            getObstacles().add(obs);
            numberObstacles--;
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void setSnakes(LinkedList<Snake> snakes) {
        this.snakes = snakes;
    }

    public LinkedList<Snake> getSnakes() {
        return snakes;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        notifyObservers();
    }

    public LinkedList<Obstacle> getObstacles() {
        return obstacles;
    }

    public synchronized Cell getObstacleCell(Obstacle obstacle) { // returns and removes Cell
        Cell toReturn = null;
        for (Cell cell : cellsWithObstacles) {
            if (cell.getGameElement().equals(obstacle)) {
                toReturn = cell;
                break;
            }
        }
        cellsWithObstacles.remove(toReturn);
        return toReturn;
    }

    public abstract void init();

    public abstract void stopSnakes();

    public abstract void handleKeyPress(int keyCode);

    public abstract void handleKeyRelease();

    public abstract int getLastKeyPressed();

    public void addSnake(Snake snake) {
        snakes.add(snake);
    }
}