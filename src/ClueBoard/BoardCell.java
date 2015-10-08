package ClueBoard;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorStatus;
	

	public BoardCell(int row, int column, char initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
	}
	
	public boolean isWalkWay()
	{
		return false;
	}
	
	public boolean isRoom()
	{
		return false;
	}
	
	public boolean isDoorWay()
	{
		return false;
	}
	
	public char getInitial() {
		return initial;
	}

	public BoardCell getBoardCell() {
		return this;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + "]";
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public DoorDirection getDoorStatus() {
		return doorStatus;
	}

}
