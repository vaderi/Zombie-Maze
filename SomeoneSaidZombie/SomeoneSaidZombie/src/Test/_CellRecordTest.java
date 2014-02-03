package Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import Cell.Cell;
import Cell.CellRecord;
import Main.Vector2;


public class _CellRecordTest {

	@Mock
	Cell mockCell;
	
	@Before
	public void mockInit()
	{
		mockCell = mock(Cell.class);
	}
	
	@Test
	public void equalsCellTest() 
	{
		CellRecord test = new CellRecord(mockCell);
		
		when( mockCell.getCoords()).thenReturn(new Vector2( 2, 3 ));
		
		assertEquals( true, test.equals( mockCell ));
	}
	
	@Test
	public void equalsCellRecordTest() 
	{
		CellRecord test = new CellRecord(mockCell);
		
		when( mockCell.getCoords()).thenReturn(new Vector2( 2, 3 ));
		
		assertEquals( true, test.equals( test ));
	}

}
