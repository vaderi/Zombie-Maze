package Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import Cell.Cell;
import Main.Maze;
import Main.Vector2;


public class _MazeTest {

	@Mock
	Vector2 mockV2;
	Cell mockCell;
	Maze mockMaze;
	
	
	
	@Before
	public void mockInit()
	{
		mockV2 = mock(Vector2.class);
		mockMaze = mock(Maze.class);
		mockCell = mock(Cell.class);
	}
	
	@Test
	public void adjacentTest() 
	{
		when(mockCell.isWall()).thenReturn(false);
		when(mockMaze.getCellAt(mockV2)).thenReturn(mockCell);
		
		ArrayList<Vector2> result = new ArrayList<Vector2>();
		
		assertEquals( result, mockMaze.getOpenAdjacent( mockV2));
	}
	
	@Test
	public void directionsTest() 
	{
		when(mockCell.isWall()).thenReturn(false);
		when(mockMaze.getCellAt(mockV2)).thenReturn(mockCell);
		
		ArrayList<Direction> result = new ArrayList<Direction>();
		
		assertEquals( result, mockMaze.getOpenDirections(mockV2));
	}
	
	
	

}
