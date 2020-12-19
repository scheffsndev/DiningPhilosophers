package diningphilosophers.impl;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import diningphilosophers.Fork;

public class SemaphoreFork implements Fork {
	
	private Semaphore semaphore = new Semaphore(1);
	private AtomicBoolean isAvailable = new AtomicBoolean(true);

	@Override
	public void take() {
		try {
			semaphore.acquire();
			isAvailable.set(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void put() {
		isAvailable.set(true);
		semaphore.release();
	}

	@Override
	public boolean isAvailable() {
		return isAvailable.get();
	}

}
