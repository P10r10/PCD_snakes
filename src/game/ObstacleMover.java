package game;

import environment.Board;
import environment.Cell;
import environment.LocalBoard;

public class ObstacleMover extends Thread {
	private Obstacle obstacle;
	private LocalBoard board;
	
	public ObstacleMover(Obstacle obstacle, LocalBoard board) {
		super();
		this.obstacle = obstacle;
		this.board = board;
	}

	@Override
	public void run() {
		// TODO
		while (obstacle.getRemainingMoves() > 0) {
			try {
				Thread.sleep(400);
				//HERE ConcurrentModificationException
				Cell cell = board.getObstacleCell(obstacle);
                cell.removeObstacle();
				board.addGameElement(obstacle); // places Obstacle in new Cell
				obstacle.decreaseMoves();
				board.setChanged();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
