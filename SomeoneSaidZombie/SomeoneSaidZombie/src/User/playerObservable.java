package User;

import Main.playerObserver;

public interface playerObservable 
{
	public void addObserver( playerObserver newObserver );
	public void removeObserver( playerObserver targetObserver );
	public void move();
}
