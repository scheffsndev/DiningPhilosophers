package diningphilosophers.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import diningphilosophers.Fork;

/**
 * This fork will lock itself by using a {@link Lock}
 * 
 * @author Stefan
 *
 */
public class LockedFork implements Fork {

	private final Lock lock = new ReentrantLock();
	private boolean isAvailable = true;

	@Override
	public void take() {
		lock.lock();
		isAvailable = false;
	}

	@Override
	public void put() {
		isAvailable = true;
		lock.unlock();
	}

	public boolean isAvailable() {
		return this.isAvailable;
	}
}
