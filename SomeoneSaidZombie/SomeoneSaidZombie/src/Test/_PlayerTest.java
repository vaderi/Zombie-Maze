package Test;
import static org.junit.Assert.*;

import org.junit.Test;

import User.Player;


public class _PlayerTest {

	@Test
	public void getterTest() 
	{
		Player test = new Player( 2, 3 );
		test.setHasKey( true );
		test.setIsDead( true );
		
		assertEquals( 2, test.getX());
		assertEquals( 3, test.getY());
		assertEquals( true, test.getHasKey() );
		assertEquals( true, test.getIsDead() );
	}

}
