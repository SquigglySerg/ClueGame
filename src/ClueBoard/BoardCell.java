package ClueBoard;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	

	public BoardCell(int row, int column, char initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
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
	

}
