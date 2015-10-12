package ClueBoard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
		roomConfigFile = "ClueLegend.txt";
		//initialize();
	}
	
	public Board(String boardConfigFileName, String roomConfigFileName)
	{
		super();
		this.adjMtx = new HashMap<>();
		this.visited = new HashSet<>();
		this.targets = new HashSet<>();
		boardConfigFile = boardConfigFileName;
		roomConfigFile = roomConfigFileName;
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
		//initialize();
	}
	
	public void initialize()
	{
		//Initialize Grid
		try {
			this.loadRoomConfig();
			this.loadBoardConfig();
			this.calcAdjacencies();
		}
		catch(BadConfigFormatException e) {
			System.out.println(e.getStackTrace());
		}
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
			rooms = new HashMap<Character, String>();
			while(scan.hasNextLine())
			{
				String line = scan.nextLine();
				//System.out.println(line.charAt(0));
				//System.out.println(line.substring(line.indexOf(' ') + 1, line.lastIndexOf(',')));
				rooms.put(line.charAt(0), line.substring(line.indexOf(' ') + 1, line.lastIndexOf(',')));
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadBoardConfig() throws BadConfigFormatException
	{
		// this.boardconfigfile = boardConfigFileName
		// read from file
		// column = numitems.row 1
		// row = num of rows
		// put characters in board cell
		FileReader reader;
		int columnCount = 1;
		ArrayList<String> hold = new ArrayList<>();
		
	try {
			reader = new FileReader(boardConfigFile);
			Scanner scan = new Scanner(reader);
			String test = scan.nextLine();
			hold.add(test);
			int count = 0;
			while(count < test.length()) {
				if(test.charAt(count) == ','){
					columnCount++;
				}
				count++;
			}
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				hold.add(line);
				count = 0;
				int check = 1;
				while(count < line.length()) {
					if(line.charAt(count) == ','){
						check++;
					}
					count++;
				}
				if(check != columnCount) {
					throw new BadConfigFormatException();
				}
			}
			scan.close();
			this.ROWS = hold.size();
			this.COLUMNS = columnCount;
			grid = new BoardCell[this.ROWS][this.COLUMNS];
			int row = 0;
			for(String piece : hold) {
				int column = 0;
				if(rooms.get(piece.charAt(0)) != null) {
				grid[row][column] = new BoardCell(row, column, piece.charAt(0));
				}
				else {
					throw new BadConfigFormatException();
				}
				for(int i = 1; i < piece.length(); i++) {
					if(piece.charAt(i) == ',') {
						if(rooms.get(piece.charAt(i + 1)) == null) {
							throw new BadConfigFormatException();
						}
						grid[row][column] = new BoardCell(row, column, piece.charAt(i + 1));
						column++;
					}
				}
				row++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
