package Strategy;
import java.util.ArrayList;
import java.util.Vector;

import Zombie.Zombie;
import Cell.Cell;
import Cell.CellRecord;
import Main.Maze;
import Main.Vector2;


public class SeekStrategy implements AttackStrategy
{

	public CellRecord getSmallest( Vector<CellRecord> list ) 
	{		
		float smallest = list.get(0).costSoFar;
		int smallestIndex = 0;
		
		for( int i = 0; i < list.size(); i++ )
		{
			if( list.get(i).costSoFar < smallest )
			{
				smallestIndex = i;
				smallest = list.get(i).costSoFar;
			}
		}
		return list.get( smallestIndex );
	}
	
	public boolean contains( Vector<CellRecord> list, Cell toFind )
	{
		for( int i = 0; i < list.size(); ++i )
		{
			if( list.get(i).equals(toFind) )
				return true;
		}
		return false;
	}
	
	private void remove( Vector<CellRecord> list, Cell toRemove )
	{
		for( int i = 0; i < list.size(); ++i )
		{
			if( list.get(i).equals(toRemove) )
				list.remove(i);
		}
	}
	
	private CellRecord getRecord( Vector<CellRecord> list, Cell toFind )
	{
		for( int i = 0; i < list.size(); ++i )
		{
			if( list.get(i).equals(toFind) )
				return list.get(i);
		}
		return null;
	}
	
	@Override
	public void play(Maze maze, Zombie zombie) 
	{
		Vector2 from = zombie.getCoords();
		Vector2 to = maze.getPlayerCoords();
		
		if( !from.equals(to) )
		{
			Vector<CellRecord> openCells = new Vector<CellRecord>();
			Vector<CellRecord> closedCells = new Vector<CellRecord>();
			
			CellRecord tempDestinationCellRecord;
			float tempCellCost;
			Cell tempDestination;
			
			CellRecord current = new CellRecord( maze.getCellAt(from) );
			current.costSoFar = 0;
			
			openCells.add(current);
			
			while( openCells.size() > 0 )
			{
				current = getSmallest( openCells );
				remove( openCells, current.getSelf() );
				
				if( current.getSelf().getCoords().equals(to) )
				{
					break;
				}
				ArrayList<Direction> links = maze.getOpenDirections( current.getSelf().getCoords() );
				
				for( int i = 0; i < links.size(); ++i )
				{
					Vector2 checkCoords = new Vector2( current.getSelf().getCoords().x, current.getSelf().getCoords().y);
					switch( links.get(i) )
	                {
	                    case EAST:
	                    	checkCoords.x = current.getSelf().getCoords().x + 1;
	                        break;
	                    case WEST:
	                    	checkCoords.x = current.getSelf().getCoords().x - 1;
	                        break;
	                    case SOUTH:
	                    	checkCoords.y = current.getSelf().getCoords().y + 1;
	                        break;
	                    case NORTH:
	                    	checkCoords.y = current.getSelf().getCoords().y - 1;
	                        break;
	                }
					
					tempDestination = maze.getCellAt( checkCoords );
					
					tempCellCost = 1 + current.costSoFar;
	
					if( contains( closedCells, tempDestination ) )//temp destination might be null
					{
						continue;
					}
					else if( contains(openCells, tempDestination) && tempCellCost >= getRecord(openCells, tempDestination).costSoFar )
					{
						continue;						
					}
					else
					{
						tempDestinationCellRecord = new CellRecord( tempDestination );
						tempDestinationCellRecord.costSoFar = tempCellCost;
						tempDestinationCellRecord.link = links.get(i);
			
						openCells.add( tempDestinationCellRecord );
					}
				}
				closedCells.add( current );
			}
			
			CellRecord backOne = current;
			
			// current should be the goal location, if the algorithm can find it at all
			if( current.getSelf().getCoords().equals(to) )
			{
				while( !current.getSelf().getCoords().equals(from) )
				{
					backOne = current;
					current = getRecord( closedCells, maze.getCellAt(current.getPreviousLocation()) );
				}
			}

			maze.getCellAt( zombie.getCoords() ).setHasZombie(false);
			zombie.moveZombie( backOne.getSelf().getCoords() );
			maze.getCellAt( zombie.getCoords() ).setHasZombie(true);
			
		}
	}
}

