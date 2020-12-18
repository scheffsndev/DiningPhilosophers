package diningphilosophers;

/**
 * A lazy philosopher, who enjoys eating much more than thinking.
 * He's the most hated person among his colleagues. I'm wondering, why?
 * @author Stefan
 *
 */
public class LazyPhilosopher extends Philosopher {

	protected static final int EAT_TIME_MULTIPLIER = 3;
	
	public LazyPhilosopher(Fork leftFork, Fork rightFork) {
		super(leftFork, rightFork);
	}
	
	@Override
	protected void eat() {
		System.out.println("Oh no, the lazy one started eating again...");
		
		acquireForks();
		
		this.cancelStarvation();
		
		try {
			Thread.sleep(EAT_TIME_MAX_MS * EAT_TIME_MULTIPLIER);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			releaseForks();
		}
		
		this.startStarvation();
		
		System.out.println("Finally he's done eating. Man, what a nuisance he is!");
	}

}
