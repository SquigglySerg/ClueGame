package clueTest;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ClueBoard.BadConfigFormatException;
import ClueBoard.Board;
import ClueBoard.BoardCell;
import ClueBoard.DoorDirection;

public class BoardTests {
	private static Board board;
	

	@Before
	public void setUpTests() 
	{
		board = new Board("ClueLayout2.csv","Legend.txt");
		board.initialize();
	}
	
	@Test
	public void testRoomLegend() throws BadConfigFormatException {
		assertEquals(11,board.getRooms().size());		// Expected 11 rooms: 9 cards, 2 others
		assertEquals("Kitchen",board.getRooms().get('K'));
		assertEquals("Dining Room",board.getRooms().get('D'));
		assertEquals("Bathroom",board.getRooms().get('B'));
		assertEquals("Office",board.getRooms().get('F'));
		assertEquals("Art Gallery",board.getRooms().get('G'));
		assertEquals("Swimming Pool",board.getRooms().get('S'));
		assertEquals("Aquarium",board.getRooms().get('A'));
		assertEquals("Bedroom",board.getRooms().get('R'));
		assertEquals("Living Room",board.getRooms().get('L'));
		assertEquals("Closet",board.getRooms().get('X'));
		assertEquals("Walkway",board.getRooms().get('W'));		
	}
	
	@Test
	public void testBoardDimensions() throws BadConfigFormatException
	{
		assertEquals(22, board.getROWS());
		assertEquals(23, board.getCOLUMNS());
	}
	
	@Test
	public void testExceptionHandling() //TODO:
	{
		try{
			board.loadBoardConfig();
		}
		catch(Exception e) {
			assertEquals("Error", e.getMessage());
		}
	}
	
	@Test
	public void testRoomInitials()
	{
		assertEquals('K',board.getCell(0, 0).getInitial());
		assertEquals('W',board.getCell(0, 11).getInitial());
		assertEquals('F',board.getCell(19, 5).getInitial());
		assertEquals('A',board.getCell(0, 21).getInitial());
		assertEquals('S',board.getCell(12, 18).getInitial());
		assertEquals('S',board.getCell(11, 20).getInitial());
		assertEquals('A',board.getCell(1, 19).getInitial());
		assertEquals('R',board.getCell(2, 13).getInitial());
		assertEquals('L',board.getCell(2, 8).getInitial());
		assertEquals('W',board.getCell(11, 6).getInitial());
		assertEquals('X',board.getCell(12, 10).getInitial());
	}
	
	//@Test
	public void testDoorDirections()
	{
		assertEquals(DoorDirection.NONE,board.getCell(0, 0).getDoorStatus());
		assertEquals(DoorDirection.NONE,board.getCell(0, 1).getDoorStatus());
		assertEquals(DoorDirection.UP,board.getCell(17, 5).getDoorStatus());
		assertEquals(DoorDirection.UP,board.getCell(20, 2).getDoorStatus());
		assertEquals(DoorDirection.LEFT,board.getCell(1, 16).getDoorStatus());
		assertEquals(DoorDirection.LEFT,board.getCell(9, 18).getDoorStatus());
		assertEquals(DoorDirection.RIGHT,board.getCell(8, 2).getDoorStatus());
		assertEquals(DoorDirection.RIGHT,board.getCell(12, 2).getDoorStatus());
		assertEquals(DoorDirection.DOWN,board.getCell(6, 6).getDoorStatus());
		assertEquals(DoorDirection.DOWN,board.getCell(7, 13).getDoorStatus());
		assertEquals(DoorDirection.NONE,board.getCell(7, 16).getDoorStatus());
	}
	
	@Test
	public void testCalcAdjacencies()
	{
		//All walkways around
		BoardCell cell = board.getCell(11,5);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(11, 4)));
		assertTrue(testList.contains(board.getCell(11, 6)));
		assertTrue(testList.contains(board.getCell(12, 5)));
		assertTrue(testList.contains(board.getCell(10, 5)));
		assertEquals(4, testList.size());
		
		//Top Left Corner of Board -- only cells of its kind around room
		cell = board.getCell(0,0);
		testList = board.getAdjList(cell);
		//assertTrue(testList.contains(board.getCell(0, 1)));
		//assertTrue(testList.contains(board.getCell(1, 0)));
		assertEquals(0, testList.size());
		
		//Bottom Left Corner of Board -- only cells of its kind around room
		cell = board.getCell(21,0);
		testList = board.getAdjList(cell);
		//assertTrue(testList.contains(board.getCell(20, 0)));
		//assertTrue(testList.contains(board.getCell(21, 1)));
		assertEquals(0, testList.size());
		
		//Top Right Corner of Board -- only cells of its kind around room
		cell = board.getCell(0,22);
		testList = board.getAdjList(cell);
		//assertTrue(testList.contains(board.getCell(0, 21)));
		//assertTrue(testList.contains(board.getCell(1, 22)));
		assertEquals(0, testList.size());
		
		//Bottom Right Corner of Board -- only cells of its kind around walkway
		cell = board.getCell(21,22);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(21, 21)));
		assertTrue(testList.contains(board.getCell(20, 22)));
		assertEquals(2, testList.size());
		
		//Cell next to room Without Doorway -- only two cells which are walkways
		cell = board.getCell(4,22);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3,22)));
		assertTrue(testList.contains(board.getCell(4,21)));
		assertEquals(2, testList.size());
		
		//Cell next to room Without Doorway -- only one walkway 
		cell = board.getCell(0,11);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1,11)));
		assertEquals(1, testList.size());
		
		//Cell next to room With Doorway -- expect 4 cells
		cell = board.getCell(8,12);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(8,13)));
		assertTrue(testList.contains(board.getCell(8,11)));
		assertTrue(testList.contains(board.getCell(7,12))); //This is door
		assertTrue(testList.contains(board.getCell(9,12)));
		assertEquals(4, testList.size());
		
		//Cell next to room With Doorway -- expect 4 cells
		cell = board.getCell(9,17);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(8,17)));
		assertTrue(testList.contains(board.getCell(10,17)));
		assertTrue(testList.contains(board.getCell(9,18))); //This is door
		assertTrue(testList.contains(board.getCell(9,16)));
		assertEquals(4, testList.size());
		
		//Cell next to room With Doorway -- expect 3 cells
		cell = board.getCell(19,2);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(19,1)));
		assertTrue(testList.contains(board.getCell(19,3)));
		assertTrue(testList.contains(board.getCell(20,2))); //This is door
		assertEquals(3, testList.size());
		
		//Cell next to room With Doorway -- expect 3 cells
		cell = board.getCell(1,15);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0,15)));
		assertTrue(testList.contains(board.getCell(2,15)));
		assertTrue(testList.contains(board.getCell(1,16))); //This is door
		assertEquals(3, testList.size());
		
		//Cell which is a Doorway -- expect 1 cells
		cell = board.getCell(6,5);
		testList = board.getAdjList(cell);
		//assertTrue(testList.contains(board.getCell(5,5))); //in room
		assertTrue(testList.contains(board.getCell(7,5))); //walkway
		//System.out.println(testList);
		assertEquals(1, testList.size());
		
		//Cell which is a Doorway -- expect 1 cells
		cell = board.getCell(17,12);
		testList = board.getAdjList(cell);
		//assertTrue(testList.contains(board.getCell(17,13))); //in room
		//assertTrue(testList.contains(board.getCell(17,11))); //in room
		//assertTrue(testList.contains(board.getCell(18,12))); //in room
		assertTrue(testList.contains(board.getCell(16,12))); //walkway
		assertEquals(1, testList.size());
	}
	
	@Test
	public void testCalcTarget()
	{
		//Walkway and no door access and 3 Away
		BoardCell cell = board.getCell(21,22);
		board.calcTargets(21, 22, 3);
		Set<BoardCell> targets = board.getTargets();
		//System.out.println("Roll of 3 targets: " + targets);		//Uncomment if test fails
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(20,22))); 
		assertTrue(targets.contains(board.getCell(21,21)));
		assertTrue(targets.contains(board.getCell(18,22)));
		assertTrue(targets.contains(board.getCell(19,21)));
		assertTrue(targets.contains(board.getCell(20,20)));
		assertTrue(targets.contains(board.getCell(21,19)));
		targets.clear();
		
		//Walkway and no door access and 6 Away
		cell = board.getCell(21,22);
		board.calcTargets(cell, 6);
		targets = board.getTargets();
		//System.out.println("Roll of 3 targets: " + targets);		//Uncomment if test fails
		//Expected Cells in targets
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(19,22))); 
		assertTrue(targets.contains(board.getCell(19,20)));
		assertTrue(targets.contains(board.getCell(18,19)));
		assertTrue(targets.contains(board.getCell(19,18)));
		assertTrue(targets.contains(board.getCell(20,21)));
		assertTrue(targets.contains(board.getCell(18,21)));
		assertTrue(targets.contains(board.getCell(20, 17)));
		assertTrue(targets.contains(board.getCell(21, 18)));
		assertTrue(targets.contains(board.getCell(20, 19)));
		assertTrue(targets.contains(board.getCell(21, 20)));
		targets.clear();
		
		//Walkway and no door access and 2 Away
		cell = board.getCell(0,11);
		board.calcTargets(cell, 2);
		targets = board.getTargets();
		//System.out.println("Roll of 3 targets: " + targets);		//Uncomment if test fails
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(2,11)));
		targets.clear();
		
		//Walkway and no door access and 4 Away
		cell = board.getCell(0,4);
		board.calcTargets(cell, 4);
		targets = board.getTargets();
		//System.out.println("Roll of 3 targets: " + targets);		//Uncomment if test fails
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(1,3))); 
		assertTrue(targets.contains(board.getCell(2,4)));
		assertTrue(targets.contains(board.getCell(3,3)));
		targets.clear();
		
		//Walkway and  door access and 6 Away -- Only checking cells in a room
		cell = board.getCell(11,5);
		board.calcTargets(cell, 6);
		targets = board.getTargets();
		//System.out.println("Roll of 3 targets: " + targets);		//Uncomment if test fails
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(6,6))); 
		assertTrue(targets.contains(board.getCell(6,5)));
		assertTrue(targets.contains(board.getCell(17,5)));
		assertTrue(targets.contains(board.getCell(12,2)));
		assertTrue(targets.contains(board.getCell(11,2)));
		assertTrue(targets.contains(board.getCell(12,2)));
		assertTrue(targets.contains(board.getCell(9,2)));
		targets.clear();
		
		//In room and  door access outside and 2 Away -- Only checking cells not in a room
		cell = board.getCell(17,12);
		board.calcTargets(cell, 2);
		targets = board.getTargets();
		//System.out.println("Roll of 3 targets: " + targets);		//Uncomment if test fails
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(15,12))); 
		assertTrue(targets.contains(board.getCell(16,13)));
		assertTrue(targets.contains(board.getCell(16,11)));
		targets.clear();
		
		//In room and  door access outside and 3 Away -- Only checking cells not in a room
		cell = board.getCell(7,9);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		//System.out.println("Roll of 3 targets: " + targets);		//Uncomment if test fails
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(8,7))); 
		assertTrue(targets.contains(board.getCell(9,8)));
		assertTrue(targets.contains(board.getCell(9,10)));
		assertTrue(targets.contains(board.getCell(8,11)));
		targets.clear();
	}
	
	/*@Test //These are meant for a 4*4 board
	public void testCalcAdjacencies()
	{	//Top Left Corner
		BoardCell cell = board.getCell(0,0);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0))); //Check for Bottom Cell
		assertTrue(testList.contains(board.getCell(0, 1))); //Check for Right Cell
		assertEquals(2, testList.size());					//Only Expect 2 Cells
		
		//Middle Section -- Second Column Middle of Grid
		cell = board.getCell(1,1);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 1))); //Check for Top Cell
		assertTrue(testList.contains(board.getCell(1, 2))); //Check for Right Cell
		assertTrue(testList.contains(board.getCell(2, 1))); //Check for Bottom Cell
		assertTrue(testList.contains(board.getCell(1, 0))); //Check for Left Cell
		assertEquals(4, testList.size());					//Only Expect 4 Cells
		
		//Bottom Right Corner
		cell = board.getCell(3,3);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3))); //Check for Top Cell
		assertTrue(testList.contains(board.getCell(3, 2))); //Check for Left Cell
		assertEquals(2, testList.size());					//Only Expect 2 Cells
		
		//Right Edge
		cell = board.getCell(1,3);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 3))); //Check for Top Cell
		assertTrue(testList.contains(board.getCell(1, 2))); //Check for Left Cell
		assertTrue(testList.contains(board.getCell(2, 3))); //Check for Bottom Cell
		assertEquals(3, testList.size());					//Only Expect 3 Cells
		
		//Left Edge
		cell = board.getCell(1,0);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 0))); //Check for Top Cell
		assertTrue(testList.contains(board.getCell(1, 1))); //Check for Right Cell
		assertTrue(testList.contains(board.getCell(2, 0))); //Check for Bottom Cell
		assertEquals(3, testList.size());					//Only Expect 3 Cells
		
		//Middle Section -- Third Column Middle of Grid
		cell = board.getCell(2,2);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 2))); //Check for Top Cell
		assertTrue(testList.contains(board.getCell(2, 3))); //Check for Right Cell
		assertTrue(testList.contains(board.getCell(3, 2))); //Check for Bottom Cell
		assertTrue(testList.contains(board.getCell(2, 1))); //Check for Left Cell
		assertEquals(4, testList.size());					//Only Expect 4 Cells
	}
	
	@Test
	public void testCalcTarget()
	{
		//Top Left Corner and 3 Away
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		//System.out.println("Roll of 3 targets: " + targets);		//Uncomment if test fails
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(3, 0))); 
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		targets.clear();
		
		//Top Left Corner and 1 Away
		cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		targets = board.getTargets();
		//System.out.println("Roll of 1 targets: " + targets);		//Uncomment if test fails
		assertEquals(2, targets.size());						//Expect 2 Cells
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(1, 0))); 
		assertTrue(targets.contains(board.getCell(0, 1)));
		targets.clear();
		
		//Top Left Corner and 2 Away
		cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		targets = board.getTargets();
		//System.out.println("Roll of 2 targets: " + targets);		//Uncomment if test fails
		assertEquals(3, targets.size());						//Expect 3 Cells ****BUT ITS 4
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(2, 0))); 
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		targets.clear();
		
		//Top Left Corner and 4 Away
		cell = board.getCell(0, 0);
		board.calcTargets(cell, 4);
		targets = board.getTargets();
		//System.out.println("Roll of 4 targets: " + targets);		//Uncomment if test fails
		assertEquals(6, targets.size());						//Expect 6 Cells *****BUT ITS 7
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(0, 2))); 
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		targets.clear();
		
		//Top Left Corner and 5 Away
		cell = board.getCell(0, 0);
		board.calcTargets(cell, 5);
		targets = board.getTargets();
		//System.out.println("Roll of 5 targets: " + targets);		//Uncomment if test fails
		assertEquals(8, targets.size());						//Expect 8 Cells
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(0, 1))); 
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 0))); 
		assertTrue(targets.contains(board.getCell(3, 2)));
		targets.clear();
		
		//Top Left Corner and 6 Away
		cell = board.getCell(0, 0);
		board.calcTargets(cell, 6);
		targets = board.getTargets();
		//System.out.println("Roll of 6 targets: " + targets);		//Uncomment if test fails
		assertEquals(7, targets.size());						//Expect 7 Cells ******BUT ITS 8
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(0, 2))); 
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		targets.clear();
		
		//Middle Section and 3 Away
		cell = board.getCell(1, 1);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		//System.out.println("Roll of 8 targets: " + targets);		//Uncomment if test fails
		assertEquals(8, targets.size());						//Expect 8 Cells
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(0, 1))); 
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(3, 2)));
	}*/
}