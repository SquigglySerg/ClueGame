package ClueBoard;

public class BoardCell {
	private int row;
	private int column;
	

	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	public BoardCell getBoardCell() {
		return this;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	

}
