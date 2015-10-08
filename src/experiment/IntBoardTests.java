package experiment;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ClueBoard.BoardCell;
import ClueBoard.IntBoard;

public class IntBoardTests {
	private IntBoard board;
	

	@Before
	public void setUpTests() 
	{
		//Will be testing with a 4x4 Grid
		board = new IntBoard(4,4);
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
		Set<BoardCell> targets = board.getTargets(cell);
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
		targets = board.getTargets(cell);
		//System.out.println("Roll of 1 targets: " + targets);		//Uncomment if test fails
		assertEquals(2, targets.size());						//Expect 2 Cells
		//Expected Cells in targets
		assertTrue(targets.contains(board.getCell(1, 0))); 
		assertTrue(targets.contains(board.getCell(0, 1)));
		targets.clear();
		
		//Top Left Corner and 2 Away
		cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		targets = board.getTargets(cell);
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
		targets = board.getTargets(cell);
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
		targets = board.getTargets(cell);
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
		targets = board.getTargets(cell);
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
		targets = board.getTargets(cell);
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