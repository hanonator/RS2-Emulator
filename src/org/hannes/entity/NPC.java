package org.hannes.entity;

import org.hannes.rs2.util.NPCDefinition;

/**
 * Represents a character not controlled by a user
 * 
 * @author red
 */
public class NPC extends Character {

	/**
	 * The type of this NPC
	 */
	private int type;

	/**
	 * 
	 * @param index
	 */
	public NPC(int index) {
		super(index);
	}

	/**
	 * 
	 * @param index
	 * @param type
	 */
	public NPC(int index, int type) {
		super(index);
		this.type = type;
	}

	@Override
	public int getAcquaintanceIndex() {
		return super.getIndex();
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	public NPCDefinition getDefinition() {
		return NPCDefinition.get(type);
	}

}