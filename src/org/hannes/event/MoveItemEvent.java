package org.hannes.event;

import org.hannes.net.Connection;

public class MoveItemEvent extends Event {

	/**
	 * The initial slot of the item
	 */
	private int source;
	
	/**
	 * the slot the item has been dragged to
	 */
	private int destination;
	
	/**
	 * The interface id of the interface the event occured on
	 */
	private int interfaceId;

	public MoveItemEvent(Connection connection) {
		super(connection);
	}

	/**
	 * @return the source
	 */
	public int getSource() {
		return source;
	}

	/**
	 * @return the destination
	 */
	public int getDestination() {
		return destination;
	}

	/**
	 * @return the interfaceId
	 */
	public int getInterfaceId() {
		return interfaceId;
	}

}