package org.hannes.event;

import org.hannes.net.Connection;

/**
 * Passed through to the player when the client events interaction
 * with an object.
 * 
 * @author red
 *
 */
public class ObjectInteractionEvent extends Event {

	/**
	 * The object's x-coordinate
	 */
	private int x;
	
	/**
	 * The object's y-coordinate
	 */
	private int y;
	
	/**
	 * The object's id
	 */
	private int id;
	
	/**
	 * The type of action performed
	 */
	private int action;

	public ObjectInteractionEvent(Connection connection) {
		super(connection);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

}