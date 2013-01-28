package org.hannes.event;

import org.hannes.net.Connection;

public class InterfaceItemEvent extends Event {
	
	/**
	 * The index of the option
	 */
	private int index;
	
	/**
	 * The slot
	 */
	private int slot;
	
	/**
	 * The item id
	 */
	private int itemId;
	
	/**
	 * The interface id
	 */
	private int interfaceId;

	public InterfaceItemEvent(Connection connection) {
		super(connection);
	}

	/**
	 * @return the slot
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @return the interfaceId
	 */
	public int getInterfaceId() {
		return interfaceId;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

}