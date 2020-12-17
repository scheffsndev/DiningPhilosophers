package diningphilosophers;

import diningphilosophers.impl.LockedFork;

public class DiningTable {
	
	private static final int MIN_NUM_FORKS = 2;

	private Philosopher[] philosophers;
	
	private static final String[] PHILOSOPHER_NAMES = {"Rudi", "Otto", "Gandalf", "Guenther", "Karl-Heinz",
														"Eberhard", "Siegfried", "Herbert", "Alfred", "Thomas"};
	
	public DiningTable(int numPhilosophers) {
		if(numPhilosophers < 1) {
			throw new IllegalArgumentException("Number of philosophers must be at least 1.");
		}
		this.philosophers = new Philosopher[numPhilosophers];
		initPhilosophers();
	}
	
	public DiningTable(Philosopher[] philosophers) {
		if (philosophers.length < 1) {
			throw new IllegalArgumentException("Number of philosophers must be at least 1.");
		}
		
		this.philosophers = philosophers;
	}
	
	
	private Fork[] createForks() {
		int numForks = this.getNumForks();
		Fork[] forks = new Fork[numForks];
		
		for (int i = 0; i < numForks; i++) {
			forks[i] = new LockedFork();
		}
		return forks;
	}
	
	private void initPhilosophers() {
		int numPhilosophers = this.getNumPhilosophers();
		
		int numForks = this.getNumForks();
		Fork[] forks = createForks();
		
		for (int i = 0; i < numPhilosophers; i++) {
			Fork leftFork = forks[i];
			Fork rightFork = forks[(i + 1) % numForks];
			
			philosophers[i] = new Philosopher(leftFork, rightFork);
		}
	}
	
	public int getNumPhilosophers() {
		return this.philosophers.length;
	}
	
	public void startSimulation() {
		for (int i = 0; i < philosophers.length; i++) {
			Philosopher philosopher = philosophers[i];
			
			int numNames = PHILOSOPHER_NAMES.length;
			String philosopherName = PHILOSOPHER_NAMES[i % numNames];
			int nameIndex = i / numNames;
			if (nameIndex > 0) {
				philosopherName += "-" + nameIndex;
			}
			
			philosopher.setDaemon(true);
			philosopher.setName(philosopherName);
			philosopher.start();
			
		}
	}
	
	
	/**
	 * Calculates the required number of forks for a certain amount of philosophers.
	 * @param numPhilosophers The number of philosophers
	 * @return The required number of forks
	 */
	public static int getNumRequiredForks(int numPhilosophers) {
		return Math.max(MIN_NUM_FORKS, numPhilosophers);
	}
	
	private int getNumForks() {
		return DiningTable.getNumRequiredForks(this.getNumPhilosophers());
	}
}
