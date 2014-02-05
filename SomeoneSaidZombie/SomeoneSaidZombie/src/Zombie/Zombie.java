package Zombie;
import Main.Vector2;
import Strategy.AttackStrategy;



public class Zombie implements NoiseObserver 
{
	Vector2 coords = new Vector2( 1, 1 );
	AttackStrategy strategy;
	private int movesTillPlay = 3;
	private int currentMoves = 0;
	private boolean ready = false;
		
	public Zombie(){ }
	
	public Zombie( int inX, int inY )
	{
		coords = new Vector2( inX, inY );
	}
	
	public Zombie( Vector2 inCoords )
	{
		coords = inCoords;
	}
	
	public void Play()
	{
		++currentMoves;
		if( currentMoves >= movesTillPlay )
		{
			ready = true;
			currentMoves = 0;
		}
	}
	
	public void moveZombie( Vector2 newCoords )
	{
		coords = newCoords;
		ready = false;
	}
	
	public void setStrategy( AttackStrategy newStrat ) { strategy = newStrat; }
	public AttackStrategy getStrategy() { return strategy; }
	public void setMovesTillPlay( int moves ) { movesTillPlay = moves; }
	public Vector2 getCoords(){ return coords; }
	public boolean isReady() { return ready; }

	@Override
	public void onNoise() 
	{
		// TODO Auto-generated method stub
		
	}
}
