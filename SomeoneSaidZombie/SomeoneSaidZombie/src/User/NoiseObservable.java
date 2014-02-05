package User;

import Zombie.NoiseObserver;

public interface NoiseObservable 
{
	public void addObserver( NoiseObserver newObserver );
	public void removeObserver( NoiseObserver targetObserver );
	public void noise();
}
