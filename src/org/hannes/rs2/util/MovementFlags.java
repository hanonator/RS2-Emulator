package org.hannes.rs2.util;

import org.hannes.util.Direction;

/**
 * an entity's movement
 * 
 * @author goku
 */
public class MovementFlags {

	/**
	 * The entity's walking direction
	 */
	private Direction primaryDirection;
	
	/**
	 * The player's running direction
	 */
	private Direction secondaryDirection;

	public Direction getPrimaryDirection() {
		return primaryDirection;
	}

	public void setPrimaryDirection(Direction primaryDirection) {
		this.primaryDirection = primaryDirection;
	}

	public Direction getSecondaryDirection() {
		return secondaryDirection;
	}

	public void setSecondaryDirection(Direction secondaryDirection) {
		this.secondaryDirection = secondaryDirection;
	}

}