package User;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import Zombie.NoiseObserver;
import Main.Vector2;
import Main.playerObserver;
import Maze.mazeItem;

public class Player extends mazeItem implements NoiseObservable, playerObservable
{
	private int x;
	private int y;
	private boolean hasKey = false;
	private boolean isDead = false;
	
	private static Set<NoiseObserver> noiseObservers = new HashSet<NoiseObserver>();
	private static Set<playerObserver> playerObservers = new HashSet<playerObserver>();
	
	public Player( int startX, int startY)
	{
		x = startX;
		y = startY;
	}
	
	public Player( Vector2 start )
	{
		x = start.x;
		y = start.y;
	}

	public int setX( int newX )
	{
		x = newX;
		
		return x;
	}
	public int setY( int newY )
	{
		y = newY;
		
		return y;
	}
	public void setLocation( Vector2 newC )
	{
		x = newC.x;
		y = newC.y;
	}
	public void setHasKey( boolean newVal ) { hasKey = newVal; }
	public void setIsDead( boolean newVal ) { isDead = newVal; }
	
	public boolean getHasKey() { return hasKey; }
	public int getX() { return x; }
	public int getY() { return y; }
	public Vector2 getLocation() { return new Vector2( x, y); }
	public boolean getIsDead() { return isDead; }

	@Override
	public void addObserver(playerObserver newObserver) 
	{
		playerObservers.add( newObserver );
	}

	@Override
	public void removeObserver(playerObserver targetObserver) 
	{
		playerObservers.remove( targetObserver );
	}

	@Override
	public void move() 
	{
		Iterator<playerObserver> iter = playerObservers.iterator();
		while( iter.hasNext() )
		{
			iter.next().onMove();
		}
	}

	@Override
	public void addObserver(NoiseObserver newObserver) 
	{
		noiseObservers.add( newObserver );
	}

	@Override
	public void removeObserver(NoiseObserver targetObserver) 
	{
		noiseObservers.remove( targetObserver );
	}

	@Override
	public void noise() 
	{
		Iterator<NoiseObserver> iter = noiseObservers.iterator();
		while( iter.hasNext() )
		{
			iter.next().onNoise();
		}
	}
	
}