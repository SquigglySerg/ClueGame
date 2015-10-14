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
	
	public void setDoorDirection(char direction) {
		if(direction == 'U') {
			doorStatus = DoorDirection.UP;
		}
		else if(direction == 'D') {
			doorStatus = DoorDirection.DOWN;
		}
		else if(direction == 'L') {
			doorStatus = DoorDirection.LEFT;
		}
		else if(direction == 'R') {
			doorStatus = DoorDirection.RIGHT;
		}
		else if(direction == 'N') {
			doorStatus = DoorDirection.NONE;
		}
	}
	
	public boolean isWalkWay()
	{
		return (initial =='W');
	}
	
	public boolean isRoom()
	{
		
		return (initial !='W');
	}
	
	public boolean isDoorway()
	{
		if(this.doorStatus != DoorDirection.NONE) {
			return true;
		}
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
	
	public DoorDirection getDoorDirection() {
		return doorStatus;
	}

}
