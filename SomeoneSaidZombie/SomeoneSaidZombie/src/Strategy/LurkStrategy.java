package Strategy;
import java.util.ArrayList;

import Main.Main;
import Main.Vector2;
import Maze.Maze;
import Zombie.Zombie;


public class LurkStrategy implements AttackStrategy {

	@Override
	public void play(Maze maze, Zombie zombie) 
	{
		ArrayList<Vector2> open = maze.getOpenAdjacent( zombie.getCoords() );
				
		int selection = Main.getRandomInt(open.size());
		
		maze.getCellAt( zombie.getCoords() ).setHasZombie(false);
		zombie.moveZombie( open.get(selection) );
		maze.getCellAt( zombie.getCoords() ).setHasZombie(true);
		
	}
	

}
