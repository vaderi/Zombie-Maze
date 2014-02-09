package Maze;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

import Main.Vector2;
import Strategy.LurkStrategy;
import Strategy.SeekStrategy;
import Strategy.WaitStrategy;
import User.Player;
import Zombie.Zombie;
import Cell.Cell;


public class Maze //implements KeyListener
{
	private final int ZOMBIE_INTERVAL = 3;
	
	private static WaitStrategy wait = new WaitStrategy();
	private static LurkStrategy lurk = new LurkStrategy();
	private static SeekStrategy seek = new SeekStrategy();
	
	private Cell[][] map;
	private int width = 21;
	private int height = 21;
	private int maxZombies = 3;
	private int zombieNum = 0;
	private Vector2 exit, key;
	private Vector2 start = new Vector2( 1, 1);
	private Player player = new Player( start );
	private Zombie[] zombies = new Zombie[maxZombies];
	private boolean hasWon = false;
	
	Random rand;
	
	//Constructor without arguments1
	//Creates new maze grid with default height and width values
	public Maze()
	{
		map = new Cell[height][width];
		rand = new Random();
	}
	
	//Constructor with arguments
	//Creates new maze grid with input values for height and width
	public Maze( int inWidth, int inHeight )
	{
		width = inWidth;
		height = inHeight;
		map = new Cell[height][width];
		rand = new Random();
	}
	
	//Generate random maze
	public void Generate()
	{
		FillMaze();
		CloseWalls();
		ConfigureMaze();
		exit = RandomSpawn();
		key = RandomSpawn();
		
		SpawnZombies();
		
	}
	
	public ArrayList<Vector2> getOpenAdjacent( Vector2 coords )
	{
		ArrayList<Vector2> open = new ArrayList<Vector2>();
		Vector2 north = new Vector2( coords.x, coords.y - 1 );
		Vector2 south = new Vector2( coords.x, coords.y + 1 );
		Vector2 east = new Vector2( coords.x + 1, coords.y );
		Vector2 west = new Vector2( coords.x - 1, coords.y );
		
		if( !getCellAt(north).isWall() )
		{
			open.add( north );
		}
		if( !getCellAt(south).isWall() )
		{
			open.add( south );
		}
		if( !getCellAt(east).isWall() )
		{
			open.add( east );
		}
		if( !getCellAt(west).isWall() )
		{
			open.add( west );
		}

		return open;
	}
	
	public ArrayList<Direction> getOpenDirections( Vector2 coords )
	{
		ArrayList<Direction> open = new ArrayList<Direction>();
		Vector2 north = new Vector2( coords.x, coords.y - 1 );
		Vector2 south = new Vector2( coords.x, coords.y + 1 );
		Vector2 east = new Vector2( coords.x + 1, coords.y );
		Vector2 west = new Vector2( coords.x - 1, coords.y );
		
		if( !getCellAt(north).isWall() )
		{
			open.add( Direction.NORTH );
		}
		if( !getCellAt(south).isWall() )
		{
			open.add( Direction.SOUTH );
		}
		if( !getCellAt(east).isWall() )
		{
			open.add( Direction.EAST );
		}
		if( !getCellAt(west).isWall() )
		{
			open.add( Direction.WEST );
		}

		return open;
	}
	
	public void movePlayer( Vector2 newCoords )
	{
		if( isValidMove( newCoords ))
			player.setLocation( newCoords );
		
		Cell current = getCellAt( player.getLocation() );
		if( current.getHasZombie() )
		{
			player.setIsDead(true);
		}
		else if( newCoords.equals(key) )
		{
			givePlayerKey();
		}
		else if( newCoords.equals(exit) )
		{
			if( player.getHasKey() )
			{
				hasWon = true;
			}
		}
	}
	
	public void Play()
	{
		for( int i = 0; i < zombies.length; ++i )
		{
			zombies[i].Play(); //update zombie
			if( zombies[i].isReady() )
			{
				zombies[i].getStrategy().play(this, zombies[i]); //play attack strategy
			}
		}
	}
	
	//Convert grid data to String output
	public String toString()
	{
		String result = "";
		String cell = "";
		
		updateZombieLocations();
		
		for( int i = 0; i < height; ++i )
		{
			for( int j = 0; j < width; ++j )
			{
				
				if( map[i][j].getHasZombie() )
					cell = "Z";
				else if( j == player.getX() && i == player.getY() )
					cell = "P";
				else if( j == key.x && i == key.y )
				{
					if( player.getHasKey() )
						cell = " ";
					else
						cell = "K";
				}
				else if( j == exit.x && i == exit.y )
					cell = "0";
				else if( map[i][j].getType() == CellType.WALL )
					cell = "#";
				else if( map[i][j].getType() == CellType.PATH )
					cell = " ";
				
				if( map[i][j].getEdge() )
					cell = "[" + cell + "]";
				else
					cell = " " + cell + " ";
				
				result += cell;
			}
			result += '\n';
		}
		if( player.getHasKey() )
			result += "\t You have the key!";
		else
			result += "\t Get to the key!";
		return result;
	}
	
	private void updateZombieLocations()
	{
		for( int i = 0; i < zombies.length; ++i )
		{
			if(zombies[i] != null)
				getCellAt(zombies[i].getCoords()).setHasZombie(true);
		}
	}
	
	public Vector2 getPlayerCoords() { return player.getLocation(); }
	public boolean isPlayerDead() { return player.getIsDead(); }
	public void killPlayer() { player.setIsDead(true); }
	public boolean hasWon() { return hasWon; }
	public void setHasWon( boolean newVal ) { hasWon = newVal; }
	public void givePlayerKey() { player.setHasKey(true); }
	
	//Fill grid with blank cell objects
	private void FillMaze()
	{
		for( int i = 0; i < height; ++i )
		{
			for( int j = 0; j < width; ++j )
			{
				map[i][j] = new Cell();
				map[i][j].setCoords(j, i);
			}
		}
	}
	
	//Rings the outer edges of the maze with walls
	private void CloseWalls()
	{
		for( int i = 0; i < height; ++i )
		{
			if( i == 0 || i%2 == 0 )
			{
				for( int j = 0; j < width; ++j )
				{
					map[i][j].setType(CellType.WALL);
					if( i == 0 || i == height - 1 )
						map[i][j].setEdge(true);
				}
			}
		}
		
		for( int j = 0; j < width; ++j )
		{
			if( j == 0 || j%2 == 0 )
			{
				for( int i = 0; i < height; ++i )
				{
					map[i][j].setType(CellType.WALL);
					if( j == 0 || j == width - 1 )
						map[i][j].setEdge(true);
				}
			}
		}
	}
	
	//Get cells 2 distant in each direction (skipping over current walls) still fully surrounded with walls
	private ArrayList<String> getNeighborsWithWalls(int x, int y)
	{
		ArrayList<String> adjacent = new ArrayList<String>();
		int checkX, checkY;
		Boolean north = false;
		Boolean south = false;
		Boolean east = false;
		Boolean west = false;
		
		if( y - 2 > 0 ) //check north if Y is still within grid
		{
			checkX = x;
			checkY = y-2;
			north = map[checkY-1][checkX].isWall() && 
					map[checkY+1][checkX].isWall() &&
					map[checkY][checkX-1].isWall() &&
					map[checkY][checkX+1].isWall();
			if( north )
				adjacent.add( "north" );
		}
		
		if( y + 2 < height ) //check south
		{
			checkX = x;
			checkY = y+2;
			south = map[checkY-1][checkX].isWall() && 
					map[checkY+1][checkX].isWall() &&
					map[checkY][checkX-1].isWall() &&
					map[checkY][checkX+1].isWall();
			if( south )
				adjacent.add( "south" );
		}
		
		if( x + 2 < width ) //check east
		{
			checkX = x+2;
			checkY = y;
			east = map[checkY-1][checkX].isWall() && 
					map[checkY+1][checkX].isWall() &&
					map[checkY][checkX-1].isWall() &&
					map[checkY][checkX+1].isWall();
			if( east )
				adjacent.add( "east" );
		}
		
		if( x - 2 > 0 ) 
		{
			checkX = x-2;
			checkY = y;
			west = map[checkY-1][checkX].isWall() && 
					map[checkY+1][checkX].isWall() &&
					map[checkY][checkX-1].isWall() &&
					map[checkY][checkX+1].isWall();
			if( west )
				adjacent.add( "west" );
		}
		
		return adjacent;
	}
	
	//Utilize recursive backtracking to generate a random maze configuration
	private void ConfigureMaze()
	{
		ArrayDeque<Cell> queue = new ArrayDeque<Cell>();
		int totalCells = (int)(width / 2) * (int)(height / 2);
		int visitedCells = 1;
		
		//Make the initial cell the current and mark it as visited
		Cell current = map[start.y][start.x];
		
		//While there are unvisited cells
		while( visitedCells < totalCells )
		{
			//get potential directions
			ArrayList<String> adj = getNeighborsWithWalls( current.getX(), current.getY() );
			if( adj.size() > 0 )
			{
				queue.push(current);
				int randNext = rand.nextInt(adj.size()); //pick random direction
				visitedCells++;
				switch( adj.get(randNext) ) //take down wall in chosen direction
				{
				case "north":
					map[current.getY() - 1][current.getX()].setType(CellType.PATH);
					current = map[current.getY() - 2][current.getX()];
					break;
				case "south":
					map[current.getY() + 1][current.getX()].setType(CellType.PATH);
					current = map[current.getY() + 2][current.getX()];
					break;
				case "east":
					map[current.getY()][current.getX() + 1].setType(CellType.PATH);
					current = map[current.getY()][current.getX() + 2];
					break;
				case "west":
					map[current.getY()][current.getX() - 1].setType(CellType.PATH);
					current = map[current.getY()][current.getX() - 2];
					break;
				}
			}
			else
			{
				current = queue.pop();
			}
		}
		
		
	}
	
	//Random spawn point generator that ensures the point is valid
	//i.e. not a wall
	private Vector2 RandomSpawn()
	{
		Boolean validPoint = false;
		Vector2 point = new Vector2();
		
		while( !validPoint )
		{
			point = new Vector2( rand.nextInt(width), rand.nextInt(height) );
			validPoint = true;
			if( map[point.y][point.x].isWall() ) validPoint = false;
		}
		
		return point;
	}
	
	//Random spawn zombies, ensuring that the point is valid and not colliding with the player or any in game objects
	private void SpawnZombies()
	{
		while( zombieNum < maxZombies )
		{
			Vector2 point = new Vector2();
			boolean isValid = false;
			
			while ( !isValid )
			{
				isValid = true;
				point = RandomSpawn();
				if( point.x == exit.x && point.y == exit.y ) isValid = false;
				if( point.x == key.x && point.y == key.y ) isValid = false;
				if( point.x == player.getX() && point.y == player.getY() ) isValid = false;
			}
			
			zombies[zombieNum] = new Zombie( point );
			map[point.y][point.x].setHasZombie(true);
			
			if( zombieNum == 0 )
			{
				zombies[zombieNum].setStrategy( wait );
			}
			else if( zombieNum == 1 )
			{
				zombies[zombieNum].setStrategy( lurk );
			}
			else if( zombieNum == 2 )
			{
				zombies[zombieNum].setStrategy( seek );
			}
			
			zombies[zombieNum].setMovesTillPlay(ZOMBIE_INTERVAL);
			
			zombieNum++;
		}
	}
	
	private boolean isValidMove( Vector2 checkCoords )
	{
		boolean result = true;
		
		if( map[checkCoords.y][checkCoords.x].isWall() ) result = false;		
		
		return result;
	}
	
	public Cell getCellAt( Vector2 coords )
	{
		return map[coords.y][coords.x];
	}
	
}
