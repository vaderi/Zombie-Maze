package Strategy;
import Zombie.Zombie;
import Maze.Maze;

public interface AttackStrategy 
{
	public void play( Maze maze, Zombie zombie );

}
