package org.hannes.util;

/**
 * 
 * 
 * @author red
 *
 */
public class PlayerDetails {

	/**
	 * The index of the player.
	 */
	private int index;
	
	/**
	 * I have no idea what this is. It is called "members" in old wL servers
	 * and is currently always sent as "1"
	 */
	private int members;

	public PlayerDetails(int index, int members) {
		this.index = index;
		this.members = members;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getMembers() {
		return members;
	}

	public void setMembers(int members) {
		this.members = members;
	}

}