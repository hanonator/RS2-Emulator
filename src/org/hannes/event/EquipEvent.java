package org.hannes.event;

import org.hannes.net.Connection;

/**
 * Sent when an item is being equipped
 * 
 * @author red
 *
 */
public class EquipEvent extends Event {
	
	/**
	 * The id of the item that has been clicked
	 */
	private int id;
	
	/**
	 * The slot in the player's inventory
	 */
	private int slot;
	
	/**
	 * The interface id
	 */
	private int interfaceId;

	public EquipEvent(Connection connection) {
		super(connection);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the slot
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * @return the interfaceId
	 */
	public int getInterfaceId() {
		return interfaceId;
	}

}