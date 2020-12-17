package diningphilosophers.impl;

import diningphilosophers.Fork;

/**
 * This fork will lock itself by using the {@code synchronized} keyword.
 * @author Stefan
 *
 */
public class SynchronizedFork implements Fork {
	
	private final Object obj = new Object();
	private boolean isAvailable = true;

	@Override
	public void take() {
		synchronized (obj) {
			isAvailable = false;
		}
	}

	@Override
	public void put() {
		synchronized (obj) {
			isAvailable = true;
		}
	}

	@Override
	public boolean isAvailable() {
		synchronized (obj) {
			return isAvailable;			
		}
	}

}
