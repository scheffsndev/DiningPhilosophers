package diningphilosophers;

public interface Fork {

	void take();
	
	void put();
	
	boolean isAvailable();
}
