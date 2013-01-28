package org.hannes.rs2.util;

/**
 * An interface on both the main game screen and the
 * sidebar
 * 
 * @author red
 *
 */
public class DoubleInterface {

	/**
	 * The interface id of the main game screen
	 */
	private final int primaryInterface;
	
	/**
	 * The interface id of the secondary interface
	 */
	private final int secondaryInterface;

	/**
	 * @param primaryInterface
	 * @param secondaryInterface
	 */
	public DoubleInterface(int primaryInterface, int secondaryInterface) {
		this.primaryInterface = primaryInterface;
		this.secondaryInterface = secondaryInterface;
	}

	/**
	 * @return the primaryInterface
	 */
	public int getPrimaryInterface() {
		return primaryInterface;
	}

	/**
	 * @return the secondaryInterface
	 */
	public int getSecondaryInterface() {
		return secondaryInterface;
	}

}