package Cell;
import Main.Vector2;

enum CellType
{
	WALL, PATH, GOAL;
}

public class Cell 
{
	private CellType type;
	private Boolean isEdge = false;
	private Boolean visited = false;
	private int xCoord;
	private int yCoord;
	private boolean hasZombie = false;
	
	public Cell()
	{
		type = CellType.PATH;
	}
	
	public Cell( CellType t )
	{
		type = t;
	}
	
	public Cell( CellType t, Boolean edge )
	{
		type = t;
		isEdge = edge;
	}
	
	public void setType( CellType t ) { type = t; }
	public void setEdge( Boolean edge ) { isEdge = edge; }
	public void setVisited( Boolean visit ) { visited = visit; }
	public void setCoords( int x, int y ) { xCoord = x; yCoord = y; }
	public void setHasZombie( boolean newVal ) { hasZombie = newVal; }
	
	public CellType getType() { return type; }
	public Boolean isWall() { return (type == CellType.WALL); }
	public Boolean getEdge() { return isEdge; }
	public Boolean getVisited() { return visited; }
	public int getX() { return xCoord; }
	public int getY() { return yCoord; }
	public Vector2 getCoords() { return new Vector2(xCoord, yCoord); }
	public boolean getHasZombie() { return hasZombie; }
	
}
