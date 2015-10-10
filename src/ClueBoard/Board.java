package ClueBoard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private int ROWS;
	private int COLUMNS;
	private String boardConfigFile;
	private String roomConfigFile;
	
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Map<Character, String> rooms;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][]  grid;
	
	public Board(){
		super();
		this.adjMtx = new HashMap<>();
		this.visited = new HashSet<>();
		this.targets = new HashSet<>();
		this.ROWS = 22;
		this.COLUMNS = 23;
		this.grid = new BoardCell[ROWS][COLUMNS];
		boardConfigFile = "ClueLayout.csv";
		roomConfigFile = "Legend.txt";
		initialize();
	}
	
	public Board(String boardConfigFileName, String roomConfigFileName)
	{
		super();
		this.adjMtx = new HashMap<>();
		this.visited = new HashSet<>();
		this.targets = new HashSet<>();
		this.ROWS = 22;
		this.COLUMNS = 23;
		this.grid = new BoardCell[ROWS][COLUMNS];
		boardConfigFile = boardConfigFileName;
		roomConfigFile = roomConfigFileName;
		initialize();
	}
	
	public Board(int Rows, int Columns){
		super();
		this.adjMtx = new HashMap<>();
		this.visited = new HashSet<>();
		this.targets = new HashSet<>();
		this.ROWS = Rows;
		this.COLUMNS = Columns;
		this.grid = new BoardCell[ROWS][COLUMNS];
		boardConfigFile = "ClueLayout.csv";
		roomConfigFile = "Legend.txt";
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
	
	public void loadRoomConfig()
	{
		// this.roomconfigfiel = roomconfigfilename;
		// read fromfile
		// read first character - key
		// read second word - value
		
		FileReader reader;
		try {
			reader = new FileReader(roomConfigFile);
			Scanner scan = new Scanner(reader);
			while(scan.hasNextLine())
			{
				String line = scan.nextLine();
				rooms.put(line.charAt(0), line.substring(line.indexOf(' '), line.lastIndexOf(',')));
			}			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void loadBoardConfig()
	{
		// this.boardconfigfile = boardConfigFileName
		// read from file
		// column = numitems.row 1
		// row = num of rows
		// put characters in board cell
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
				if(!visited.contains(i))
					targets.add(i);
			else
				if(!visited.contains(i))
					calcTargets(i, pathLength-1);
			//visited.remove(i);
		}
		visited.remove(initialCell);
	}
	
	public void calcTargets(int row, int column, int pathLength)
	{
		calcTargets(grid[row][column], pathLength);
	}

	public Set<BoardCell> getTargets()
	{
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(int i, int j) {
		return this.getAdjList(grid[i][j]);
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell cell)
	{
		return adjMtx.get(cell);
	}

	public int getROWS() {
		return ROWS;
	}
	
	public int getNumRows()
	{
		return ROWS;
	}

	public int getCOLUMNS() {
		return COLUMNS;
	}
	
	public int getNumColumns()
	{
		return COLUMNS;
	}
	
	public Map<Character, String> getRooms() {
		return rooms;
	}

	public BoardCell getCell(int row,int column)
	{
		return grid[row][column];
	}
	
	public BoardCell getCellAt(int row,int column)
	{
		return grid[row][column];
	}
	
}
