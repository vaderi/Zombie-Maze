package Test;
import static org.junit.Assert.*;

import org.junit.Test;

import Cell.Cell;


public class _CellTest {

	@Test
	public void gettersTest() 
	{
		Cell test = new Cell();
		assertEquals( CellType.PATH, test.getType() );
		assertEquals( false, test.isWall() );
		assertEquals( false, test.getEdge() );
		assertEquals( false, test.getVisited() );
		assertEquals( false, test.getHasZombie() );
	}

}
