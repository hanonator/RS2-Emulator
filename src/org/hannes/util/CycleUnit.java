package org.hannes.util;

/**
 * A unit to specify at which a task should cycle
 * 
 * @author red
 */
public enum CycleUnit {

	/**
	 * A server cycle is the rate at which the server updates. It is scheduled
	 * to run at 50ms intervals.
	 */
	SERVER_CYCLE(1),
	
	/**
	 * A game cycle is the rate at which the client should update, this
	 * time is 600ms or 12 server cycles.
	 */
	GAME_CYCLE(12),
	
	/**
	 * A minute is 1200 server cycles or 60,000ms
	 */
	MINUTE(1200),
	
	/**
	 * An hour is 7200 server cycles or 3,600,000ms
	 */
	HOUR(72000);

	/**
	 * The amount of server cycles it takes to reach the amount of cycles needed for this
	 * unit time
	 */
	private final int cycles;

	private CycleUnit(int cycles) {
		this.cycles = cycles;
	}

	public int getCycles() {
		return cycles;
	}

}