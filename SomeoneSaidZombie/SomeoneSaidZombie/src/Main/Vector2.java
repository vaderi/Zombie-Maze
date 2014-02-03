package Main;

public class Vector2 {
	
	public int x, y;
	
	public Vector2()
	{
		x = 0;
		y = 0;
	}
	
	public Vector2( int inX, int inY )
	{
		x = inX;
		y = inY;
	}
	
	public Vector2( Vector2 inV )
	{
		x = inV.x;
		y = inV.y;
	}
	
	public boolean equals( Vector2 rhs )
	{
		return ( x == rhs.x && y == rhs.y );
	}
	
}
