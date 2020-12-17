package diningphilosophers;

import java.util.Random;

public class Philosopher extends Thread {
	
	private static final int THINK_TIME_MIN_MS = 5000;
	private static final int THINK_TIME_MAX_MS = 15000;
	private static final int EAT_TIME_MIN_MS = 1000;
	private static final int EAT_TIME_MAX_MS = 5000;
	
	private static final Random random = new Random();
	
	private final Fork leftFork;
	private final Fork rightFork;
	
	public Philosopher(Fork leftFork, Fork rightFork) {
		this.leftFork = leftFork;
		this.rightFork = rightFork;
	}

	private void eat() {
		
		System.out.println(this.toString() + " just started eating.");
		
		int eatTime = getRandInt(EAT_TIME_MIN_MS, EAT_TIME_MAX_MS);
		
		acquireForks();
		try {
			Thread.sleep(eatTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			releaseForks();
		}
		
		System.out.println(this.toString() + " just finished eating.");
	}

	private void think() {
		
		System.out.println(this.toString() + " just started thinking.");
		
		int thinkTime = getRandInt(THINK_TIME_MIN_MS, THINK_TIME_MAX_MS);
		try {
			Thread.sleep(thinkTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(this.toString() + " just finished thinking.");
	}
	
	private void acquireForks() {
		System.out.println(this.toString() + " just started to acquire resources.");
		
		leftFork.take();
		
		System.out.println(this.toString() + " just acquired left fork.");
		
		rightFork.take();
		
		System.out.println(this.toString() + " just acquired right fork.");
	}
	
	private void releaseForks() {
		leftFork.put();
		rightFork.put();
	}
	
	private int getRandInt(int min, int max) {
		return random.nextInt(max - min) + min;
	}

	@Override
	public void run() {
		while(true) {
			this.think();
			this.eat();
		}
	}

}
