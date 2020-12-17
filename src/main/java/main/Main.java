package main;

import diningphilosophers.DiningTable;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}
	
	private void start() {
		DiningTable table = new DiningTable(3);
		
		table.startSimulation();
		
		try {
			Thread.sleep(120 * 1000 * 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
