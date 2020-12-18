package diningphilosophers;

/**
 * A greedy philosopher. He doesn't want to give up any resource he'd claimed earlier.
 * His natural habitat is a lonely corner in the dark, because nobody likes him as a friend.
 * @author Stefan
 *
 */
public class GreedyPhilosopher extends Philosopher {

	public GreedyPhilosopher(Fork leftFork, Fork rightFork) {
		super(leftFork, rightFork);
	}
	
	@Override
	protected void releaseForks() {
		// Too bad, this poor guy doesn't want to give up the resources he'd claimed.
	}
}
