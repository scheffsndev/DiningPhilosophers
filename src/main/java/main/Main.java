package main;

import java.time.Duration;
import diningphilosophers.DiningTable;
import diningphilosophers.Fork;
import diningphilosophers.GreedyPhilosopher;
import diningphilosophers.LazyPhilosopher;
import diningphilosophers.Philosopher;
import diningphilosophers.impl.LockedFork;

public class Main {
	
	private static final int NUM_PHILOSOPHERS = 5;

	public static void main(String[] args) {
		Main main = new Main();
		main.startNormal();
	}

	private void startNormal() {
		DiningTable table = new DiningTable(NUM_PHILOSOPHERS);

		table.startSimulation();

		Duration sleepTime = Duration.ofMinutes(3);

		try {
			Thread.sleep(sleepTime.toMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		table.endSimulation(true);
		System.exit(0);
	}
	
	/**
	 * Starts the simulation with three philosophers, but has one impostor among them.
	 */
	private void startLazy() {
		
		Fork f1 = new LockedFork();
		Fork f2 = new LockedFork();
		Fork f3 = new LockedFork();
		
		Philosopher[] philosophers = new Philosopher[] { new LazyPhilosopher(f1, f2),
				new Philosopher(f2, f3),
				new Philosopher(f3, f1)};
		
		DiningTable table = new DiningTable(philosophers);
		table.startSimulation();
		
		Duration sleepTime = Duration.ofMinutes(3);
		try {
			Thread.sleep(sleepTime.toMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		table.endSimulation(true);
		System.exit(0);
	}
	
	/**
	 * Starts the simulation with three philosophers, but has a greedy one. People are going to starve, for sure.
	 */
	private void startGreedy() {
		
		Fork f1 = new LockedFork();
		Fork f2 = new LockedFork();
		Fork f3 = new LockedFork();
		
		Philosopher[] philosophers = new Philosopher[] { new GreedyPhilosopher(f1, f2),
				new Philosopher(f2, f3),
				new Philosopher(f3, f1)};
		
		DiningTable table = new DiningTable(philosophers);
		table.startSimulation();
		
		Duration sleepTime = Duration.ofMinutes(3);
		try {
			Thread.sleep(sleepTime.toMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		table.endSimulation(true);
		System.exit(0);
	}
}
