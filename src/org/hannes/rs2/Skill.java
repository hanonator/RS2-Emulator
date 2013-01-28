package org.hannes.rs2;

/**
 * 
 * 
 * @author red
 */
public class Skill {

	/**
	 * The index of the skill (in the client)
	 */
	private final int index;

	/**
	 * The experience the player has in this skill
	 */
	private int experience;
	
	/**
	 * The level the player has in this skill (this can differ from the
	 * absolute level based on the current experience)
	 */
	private int level;

	public Skill(int index) {
		this(index, 0, 1);
	}

	public Skill(int index, int experience, int level) {
		this.index = index;
		this.experience = experience;
		this.level = level;
	}

	public Skill set(int level, int exp) {
		this.level = level;
		this.experience = exp;
		return this;
	}

	public int getIndex() {
		return index;
	}

	public int getExperience() {
		return experience;
	}

	public Skill setExperience(int experience) {
		this.experience = experience;
		return this;
	}

	public int getLevel() {
		return level;
	}

	public Skill setLevel(int level) {
		this.level = level;
		return this;
	}

}