package Main;
import java.util.Random;




public class Main {

	public enum GameState
	{
		GENERATE, PLAY, QUIT
	}
	
	public static GameState gState;
	private static Game game;
	private static Random rand = new Random();
	
	public static void main(String[] args) 
	{
		game = new Game();
		gState = GameState.GENERATE;
		
		while( gState != GameState.QUIT )
		{
			if( gState == GameState.GENERATE )
			{	
				game.Initialize();
				gState = GameState.PLAY;
			}
			else if( gState == GameState.PLAY )
			{
				game.Play();
				if( game.isPlayerDead() || game.hasWon())
				{
					game.clear();
					gState = GameState.GENERATE;
				}
			}
		}
		
		System.exit(0);
	}
	
	public static int getRandomInt( int max )
	{
		return rand.nextInt(max);
	}

}
