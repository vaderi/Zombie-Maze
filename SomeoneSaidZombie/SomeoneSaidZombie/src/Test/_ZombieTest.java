package Test;
import static org.junit.Assert.*;

import org.junit.Test;

import Zombie.Zombie;


public class _ZombieTest {

	@Test
	public void gettersTest() 
	{
		Zombie test = new Zombie(2, 3);
		
		assertEquals( false, test.isReady());
	}

}
