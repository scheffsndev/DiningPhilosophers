package diningphilosophers;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Philosopher extends Thread {

	protected static final int THINK_TIME_MIN_MS = 5000;
	protected static final int THINK_TIME_MAX_MS = 15000;
	protected static final int EAT_TIME_MIN_MS = 1000;
	protected static final int EAT_TIME_MAX_MS = 5000;

	private static final Random random = new Random();
	private static ScheduledExecutorService execService = Executors.newSingleThreadScheduledExecutor();

	private final Fork leftFork;
	private final Fork rightFork;

	private AtomicBoolean aliveBoolean = new AtomicBoolean(true);
	private AtomicBoolean exitRequested = new AtomicBoolean(false);
	private ScheduledFuture<?> starvationFuture;

	public Philosopher(Fork leftFork, Fork rightFork) {
		this.leftFork = leftFork;
		this.rightFork = rightFork;
	}

	protected void eat() {

		System.out.println(this.toString() + " just started eating.");

		int eatTime = getRandInt(EAT_TIME_MIN_MS, EAT_TIME_MAX_MS);

		acquireForks();
		
		this.cancelStarvation();
		
		try {
			Thread.sleep(eatTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		} finally {
			releaseForks();
		}
		
		this.startStarvation();

		System.out.println(this.toString() + " just finished eating.");
	}

	protected void think() {

		System.out.println(this.toString() + " just started thinking.");

		int thinkTime = getRandInt(THINK_TIME_MIN_MS, THINK_TIME_MAX_MS);
		try {
			Thread.sleep(thinkTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(this.toString() + " just finished thinking.");
	}

	protected void acquireForks() {
		System.out.println(this.toString() + " just started to acquire resources.");

		leftFork.take();

		System.out.println(this.toString() + " just acquired left fork.");

		rightFork.take();

		System.out.println(this.toString() + " just acquired right fork.");
	}

	private Duration getStarvationDuration() {
		return Duration.ofMillis((long) ((EAT_TIME_MAX_MS + THINK_TIME_MAX_MS) * 1.25));
	}

	protected void releaseForks() {
		leftFork.put();
		rightFork.put();
	}

	private int getRandInt(int min, int max) {
		return random.nextInt(max - min) + min;
	}

	protected void cancelStarvation() {
		if (starvationFuture != null) {
			starvationFuture.cancel(false);
		}
	}

	protected void startStarvation() {
		Duration starvationDuration = this.getStarvationDuration();
		starvationFuture = execService.schedule(() -> {
			System.out.println("WARNING: " + this.toString() + " just died of starvation!");
			aliveBoolean.set(false);
		}, starvationDuration.toMillis(), TimeUnit.MILLISECONDS);
	}

	@Override
	public void start() {
		super.start();
		this.startStarvation();
	}

	@Override
	public void run() {
		while (aliveBoolean.get()) {
			
			if (exitRequested.get()) {
				this.cancelStarvation();
				System.out.println(this.toString() + " says: I'm done for now. You better pay me decently for all my hard work.");
				break;
			}
			
			this.think();
			this.eat();
		}
	}
	
	public void exitGracefully() {
		exitRequested.set(true);
	}

}
