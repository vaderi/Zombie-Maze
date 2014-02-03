package Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import Cell.Cell;
import Cell.CellRecord;
import Main.Vector2;
import Strategy.SeekStrategy;


public class _SeekStrategyTest {

	@Mock
	Cell mockCell;
	@Mock
	CellRecord mockRecord;
	
	
	@Before
	public void mockInit()
	{
		mockCell = mock(Cell.class);
		mockRecord = mock(CellRecord.class);
	}
	
	@Test
	public void containsTest() 
	{
		SeekStrategy test = new SeekStrategy();
		Vector<CellRecord> testList = new Vector<CellRecord>();
		testList.add( new CellRecord( mockCell ));
		
		when(mockRecord.equals(mockCell)).thenReturn(true);
		when(mockCell.getCoords()).thenReturn( new Vector2(1,1) );
		
		assertEquals( true, test.contains( testList, mockCell ));
	}

}
