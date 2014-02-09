package Panels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.playerObserver;

public class controlPanel extends JPanel implements playerObserver
{
	private JLabel turnLeftLabel;
	private JButton fireButton;
	
	@Override
	public void onMove() 
	{
		
	}
}
