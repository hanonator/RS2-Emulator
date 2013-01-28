package org.hannes.rs2.content.common;

import org.hannes.entity.RSObject;

/**
 * Represents a door
 * 
 * @author red
 *
 */
public class Door extends RSObject {

	/**
	 * The door that is linked to this door. Cool RSPS
	 * people call these "double doors"
	 */
	private Door link;

	/**
	 * The orientation of the door in idle position
	 */
	private int defaultOrientation;

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param defaultOrientation
	 */
	public Door(int defaultOrientation) {
		this.defaultOrientation = defaultOrientation;
	}

	/**
	 * @return the defaultOrientation
	 */
	public int getDefaultOrientation() {
		return defaultOrientation;
	}

	/**
	 * @param door
	 */
	public void link(Door door) {
		this.link = door;
	}

	/**
	 * @return the link
	 */
	public Door getLink() {
		return link;
	}

}