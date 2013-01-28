package org.hannes.event;

import org.hannes.net.Connection;

/**
 * The event for walking
 * 
 * @author red
 */
public class WalkEvent extends Event {

	/**
	 * Indicates the person is running
	 */
	private boolean running;
	
	/**
	 * The initial X position
	 */
	private int initialX;
	
	/**
	 * The initial Y position
	 */
	private int initialY;
	
	/**
	 * The walking-queue data
	 */
	private byte[] data;

	public WalkEvent(Connection connection) {
		super(connection);
	}

	public boolean isRunning() {
		return running;
	}

	public int getInitialX() {
		return initialX;
	}

	public int getInitialY() {
		return initialY;
	}

	public byte[] getData() {
		return data;
	}

	public byte getData(int i) {
		return data[i];
	}

}