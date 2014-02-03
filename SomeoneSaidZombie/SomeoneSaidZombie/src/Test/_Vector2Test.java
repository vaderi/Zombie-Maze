package Test;
import static org.junit.Assert.*;

import org.junit.Test;

import Main.Vector2;


public class _Vector2Test {

	@Test
	public void equalsTest() 
	{
		Vector2 test = new Vector2( 2, 3 );
		Vector2 test2 = new Vector2( 3, 5 );
		
		assertEquals(false, test.equals(test2));
		assertEquals(true, test.equals(test));
	}

}
