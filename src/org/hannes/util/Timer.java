package org.hannes.util;

/**
 * A timer based on the scheduler's cycles
 * 
 * @author goku
 */
public class Timer {

	/**
	 * The last time the cyclecount was received from a given GameEngine 
	 */
	private long clock;

	/**
	 * The engine to which the clock refers
	 */
	private final GameEngine engine;

	/**
	 * Create a timer based on a given engine
	 * 
	 * @param engine
	 */
	public Timer(GameEngine engine) {
		this.engine = engine;
	}

	/**
	 * Check the clock time with the game engine's clock time
	 * 
	 * @param time
	 * @param unit
	 * @return
	 */
	public boolean check(long time, CycleUnit unit) {
		return (engine.getClock() - clock) / unit.getCycles() >= time;
	}

	/**
	 * Mark the current clock time
	 */
	public void clock() {
		this.clock = engine.getClock();
	}

}