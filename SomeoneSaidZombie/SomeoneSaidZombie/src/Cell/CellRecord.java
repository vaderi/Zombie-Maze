package Cell;
import Main.Vector2;


enum Direction
{
	NORTH, SOUTH, EAST, WEST;
}

public class CellRecord 
{
	private Cell self;
	public float costSoFar;
	public Direction link = Direction.NORTH;
	
	public CellRecord( Cell record )
	{
		self = record;
	}
	
	public Vector2 getPreviousLocation()
	{
		switch( link )
		{
		case NORTH:
			return new Vector2( self.getX(), self.getY() + 1 );
		case SOUTH:
			return new Vector2( self.getX(), self.getY() - 1 );
		case EAST:
			return new Vector2( self.getX() - 1, self.getY() );
		case WEST:
			return new Vector2( self.getX() + 1, self.getY() );
		default:
			return self.getCoords();
		}
	}
	
	public Cell getSelf() { return self; }
	public void setSelf( Cell newSelf ) { self = newSelf; }
	public boolean equals( Cell rhs ) { return ( self.getCoords().equals( rhs.getCoords()) ); }
	public boolean equals( CellRecord rhs ) { return ( self.getCoords().equals(rhs.getSelf().getCoords()) ); }
}
