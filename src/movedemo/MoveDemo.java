package movedemo;

public class MoveDemo {
	public static void main(String[] args) {
		new MoveDemo();
	}
	
	Position start = null;
	Position target = null;
	
	public MoveDemo() {
		start = new Position(1,1);
		target = new Position(3,4);
		
		Position[] path = calcPath2(start, target);
		for (int i = 0; i < path.length; i++) {
			System.out.println(path[i]);
		}
	}
	
	public static Position[] calcPath(Position start, Position target) {
		Position[] path = new Position[start.distanceTo(target) + 1];
		path[0] = start;
		int direction = 0;
		int index = 1;
		while (index < path.length) {
			Position next = null;
			if (path[index-1].getRow() != target.getRow() && direction == 0) {
				if (path[index-1].getRow() < target.getRow()) {
					next = new Position(path[index-1].getRow() + 1, path[index-1].getCol());
				} else if (path[index-1].getRow() > target.getRow()) {
					next = new Position(path[index-1].getRow() - 1, path[index-1].getCol());
				}
			} else if (path[index-1].getCol() != target.getCol() && direction == 1) {
				if (path[index-1].getCol() < target.getCol()) {
					next = new Position(path[index-1].getRow(), path[index-1].getCol() + 1);
				} else if (path[index-1].getCol() > target.getCol()) {
					next = new Position(path[index-1].getRow(), path[index-1].getCol() - 1);
				}
			}
			if (next != null) {
				path[index] = next;
				index++;
			}
			direction = (direction + 1) % 2;
		}
		return path;
	}
	
	public static Position[] calcPath2(Position start, Position target) {
		Position[] path = new Position[start.distanceTo(target) + 1];
		path[0] = start;
		int rows = Math.abs(start.getRow() - target.getRow());
		int cols = Math.abs(start.getCol() - target.getCol());
		int dRow;
		int dCol;
		if (rows >= cols) {
			dRow = rows/cols;
			dCol = 1;
		} else {
			dRow = 1;
			dCol = cols/rows;
		}
		int index = 1;
		int direction = 0;
		while (index < path.length) {
			System.out.println("WHILE: " + path[index-1]);
			if (path[index-1].getRow() != target.getRow() && direction == 0) {
				System.out.println("ROW");
				for (int i = 0; i < dRow; i++) {
					System.out.println("ROW: FOR");
					int rowStep = path[index-1].getRow() < target.getRow() ? 1 : -1;
					System.out.println("ROW STEP: " + rowStep);
					path[index] = new Position(path[index-1].getRow() + rowStep, path[index-1].getCol());
					if (path[index].getRow() == target.getRow()) {
						System.out.println("ROW: break; " + path[index]);
						index++;
						break;
					}
					index++;
				}
			}
			if (path[index-1].getCol() != target.getCol() && direction == 1) {
				System.out.println("COL");
				for (int i = 0; i < dCol; i++) {
					System.out.println("COL: FOR");
					int colStep = path[index-1].getCol() < target.getCol() ? 1 : -1;
					System.out.println("COL: STEP " + colStep);
					path[index] = new Position(path[index-1].getRow(), path[index-1].getCol() + colStep);
					if (path[index].getCol() == target.getCol()) {
						System.out.println("COL: break");
						index++;
						break;
					}
					index++;
				}
			}
			direction = (direction + 1) % 2;
		}
		return path;
	}
}
