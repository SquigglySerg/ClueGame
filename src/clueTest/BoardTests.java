package clueTest;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ClueBoard.Board;
import ClueBoard.BoardCell;
import ClueBoard.DoorDirection;

public class BoardTests {
	private Board board;
	

	@Before
	public void setUpTests() 
	{
		//Will be testing with a 4x4 Grid
		board = new Board(4,4);
	}
	
	@Test
	public void testRoomLegend() {
		board.loadRoomConfig();
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
	public void testBoardDimensions()
	{
		board.loadBoardConfig();
		assertEquals(21, board.getROWS());
		assertEquals(20, board.getCOLUMNS());
	}
	
	@Test
	public void testExceptionHandling() //TODO:
	{
		try{
			board.loadBoardConfig();
			fail("blah");
		}
		catch(Exception e) {
			assertEquals("Error", e.getMessage());
		}
	}
	
	@Test
	public void testRoomInitials()
	{
		assertEquals('K',board.getCell(0, 0).getInitial());
		assertEquals('D',board.getCell(0, 11).getInitial());
		assertEquals('F',board.getCell(19, 5).getInitial());
		assertEquals('B',board.getCell(0, 21).getInitial());
		assertEquals('G',board.getCell(12, 18).getInitial());
		assertEquals('S',board.getCell(11, 20).getInitial());
		assertEquals('A',board.getCell(1, 19).getInitial());
		assertEquals('R',board.getCell(2, 13).getInitial());
		assertEquals('L',board.getCell(2, 8).getInitial());
		assertEquals('W',board.getCell(11, 6).getInitial());
		assertEquals('X',board.getCell(12, 10).getInitial());
	}
	
	@Test
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
		assertEquals(6, targets.size());						//Expect 6 Cells
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
	}
}