package Strategy;
import Zombie.Zombie;
import Main.Maze;

public interface AttackStrategy 
{
	public void play( Maze maze, Zombie zombie );

}
