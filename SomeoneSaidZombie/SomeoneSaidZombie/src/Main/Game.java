package Main;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import Maze.Maze;


public class Game implements KeyListener
{
	private Maze maze;
	private JFrame playFrame = new JFrame();
	private JTextArea writeArea = new JTextArea();
	
	public Game()
	{
		//Code provided for UI
		playFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		writeArea.setFont(new Font( "monospaced", Font.PLAIN, 12 ));
		writeArea.setEditable(false);
		
		playFrame.getContentPane().add( writeArea );
		playFrame.setResizable(false);
		playFrame.setVisible(true);
		playFrame.setFocusable(true);
		playFrame.addKeyListener(this);
	}
	
	public void Initialize()
	{
		maze = new Maze(21, 21);
		maze.Generate();
		updateText();
	}
	
	public void clear()
	{
		writeArea.setText("");
		maze = null;
	}
	
	public void Play()
	{
		updateText();
	}
	
	private void updateText()
	{
		writeArea.setText(maze.toString());
		playFrame.pack(); //resize window to fit subcomponent
	}

	public boolean isPlayerDead() { return maze.isPlayerDead(); }
	public boolean hasWon() { return maze.hasWon(); }
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		Vector2 newCoords = new Vector2( maze.getPlayerCoords() );
		boolean playerMoved = false;

		switch( e.getKeyCode() ) 
	    {
	    case KeyEvent.VK_K:
	    	maze.killPlayer();
	    	break;
	    case KeyEvent.VK_Q:
	    	Main.gState = Main.GameState.QUIT;
	    	break;
	    case KeyEvent.VK_P:
	    	maze.Play();
	    	break;
		case KeyEvent.VK_W:
			maze.setHasWon(true);
			break;
		case KeyEvent.VK_Y:
			maze.givePlayerKey();
			break;
		case KeyEvent.VK_UP:
			newCoords.y--;
			break; 
	    case KeyEvent.VK_DOWN:
	    	newCoords.y++;
	        break; 
	    case KeyEvent.VK_LEFT:
	    	newCoords.x--;
	        break; 
	    case KeyEvent.VK_RIGHT:
	    	newCoords.x++;
	        break; 	
	    }
		
		if( !maze.getCellAt( newCoords ).isWall() )
		{
			maze.movePlayer( newCoords );
			playerMoved = true;
		}
		if( playerMoved )
		{
			maze.Play();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e) { }
	
	


}
