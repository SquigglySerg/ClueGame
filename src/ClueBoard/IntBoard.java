package ClueBoard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private int ROWS;
	private int COLUMNS;
	private String boardConfigFile;
	private String roomConfigFile;
	
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Map<Character, String> rooms;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][]  grid;
	
	public IntBoard(){
		super();
		this.adjMtx = new HashMap<>();
		this.visited = new HashSet<>();
		this.targets = new HashSet<>();
		this.ROWS = 22;
		this.COLUMNS = 23;
		this.grid = new BoardCell[ROWS][COLUMNS];
		initialize();
	}
	
	public IntBoard(int Rows, int Columns){
		super();
		this.adjMtx = new HashMap<>();
		this.visited = new HashSet<>();
		this.targets = new HashSet<>();
		this.ROWS = Rows;
		this.COLUMNS = Columns;
		this.grid = new BoardCell[ROWS][COLUMNS];
		initialize();
	}
	
	public void initialize()
	{
		/*//Initialize Grid
		for(int i = 0; i < ROWS;i++)
		{
			for(int j = 0; j < COLUMNS; j++)
			{
				grid[i][j] = new BoardCell(i,j);
			}
		}
		this.calcAdjacencies();*/
	}
	
	public void loadRoomConfig(String roomConfigFileName)
	{
		// this.roomconfigfiel = roomconfigfilename;
		// read fromfile
		// read first character - key
		// read second word - value
	}
	
	public void loadBoardConfig(String boardConfigFileName)
	{
		// this.boardconfigfile = boardConfigFileName
		// read from file
		// column = numitems.row 1
		// row = num of rows
		// put characters in board cell
	}
	
	public Map<Character, String> getRooms() {
		return rooms;
	}

	public BoardCell getCell(int row,int column)
	{
		return grid[row][column];
	}
	
	public void calcAdjacencies()
	{
		for(int i = 0; i < ROWS;i++)
		{
			for(int j = 0; j < COLUMNS; j++)
			{
				LinkedList<BoardCell> adjList = new LinkedList<BoardCell>();
				//Add TOP, BOTTOM, RIGHT, and LEFT Cells to list if they exist
				if(i+1 < ROWS){adjList.add(grid[i+1][j]);}
				if(i-1 >= 0){adjList.add(grid[i-1][j]);}
				if(j+1 < COLUMNS){adjList.add(grid[i][j+1]);}
				if(j-1 >= 0){adjList.add(grid[i][j-1]);}
				adjMtx.put(grid[i][j], adjList);
			}
		}
	}
	
	public void calcTargets(BoardCell initialCell, int pathLength)
	{
		visited.add(initialCell);
		for(BoardCell i : adjMtx.get(initialCell))
		{
			//visited.add(i);
			if(pathLength == 1)
			{
				if(!visited.contains(i))
					targets.add(i);
			}
			else
				if(!visited.contains(i))
					calcTargets(i, pathLength-1);
			//visited.remove(i);
		}
		visited.remove(initialCell);
	}

	public Set<BoardCell> getTargets(BoardCell cell)
	{
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell cell)
	{
		return adjMtx.get(cell);
	}

	public int getROWS() {
		return ROWS;
	}

	public int getCOLUMNS() {
		return COLUMNS;
	}
	
	
	
}
