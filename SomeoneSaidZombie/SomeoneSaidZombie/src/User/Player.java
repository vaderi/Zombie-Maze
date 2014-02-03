package User;
import Main.Vector2;

public class Player 
{
	private int x;
	private int y;
	private boolean hasKey = false;
	private boolean isDead = false;
	
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
	
}