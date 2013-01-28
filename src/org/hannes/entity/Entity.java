package org.hannes.entity;

import org.hannes.locale.Location;
import org.hannes.locale.Region;

/**
 * An {@link Entity} is an element of the game that responds to an interaction
 * 
 * @author red
 *
 */
public class Entity {

	/**
	 * The index of the entity
	 */
	private int index;
	
	/**
	 * The location of the entity
	 */
	private Location location = new Location(Location.NULL_LOCATION);
	
	/**
	 * The region this entity is currently in
	 */
	private Region region = location.region();

	/**
	 * Create a new entity at a given index
	 * 
	 * @param index
	 */
	public Entity(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

}