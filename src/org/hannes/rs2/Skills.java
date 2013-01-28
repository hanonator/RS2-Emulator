package org.hannes.rs2;

import org.hannes.entity.Player;
import org.hannes.entity.sync.UpdateFlags.UpdateFlag;

public class Skills {
	
	/**
	 * The number of skills.
	 */
	public static final int SKILL_COUNT = 22;
	
	/**
	 * The largest allowed experience.
	 */
	public static final int MAXIMUM_EXP = 200000000;
	
	/**
	 * The skill names.
	 */
	public static final String[] SKILL_NAME	= { "Attack", "Defence",
		"Strength", "Hitpoints", "Range", "Prayer",
		"Magic", "Cooking", "Woodcutting", "Fletching",
		"Fishing", "Firemaking", "Crafting", "Smithing",
		"Mining", "Herblore", "Agility", "Thieving",
		"Slayer", "Farming", "Runecrafting", "Hunter" };

	/**
	 * Constants for the skill numbers.
	 */
	public static final int	ATTACK	= 0, DEFENCE = 1, STRENGTH = 2,
		HITPOINTS = 3, RANGE = 4, PRAYER = 5, MAGIC = 6,
		COOKING = 7, WOODCUTTING = 8, FLETCHING = 9,
		FISHING = 10, FIREMAKING = 11, CRAFTING = 12,
		SMITHING = 13, MINING = 14, HERBLORE = 15,
		AGILITY = 16, THIEVING = 17, SLAYER = 18,
		FARMING = 19, RUNECRAFTING = 20;

	/**
	 * The player object.
	 */
	private final Player player;
	
	/**
	 * The skills
	 */
	private final Skill[] skills = new Skill[SKILL_COUNT];
	
	/**
	 * Creates a skills object.
	 * 
	 * TODO: Loading
	 * @param player The player whose skills this object represents.
	 */
	public Skills(Player player) {
		this.player = player;
		for(int i = 0; i < SKILL_COUNT; i++) {
			skills[i] = new Skill(i, 0, 1);
		}
		skills[HITPOINTS] = new Skill(HITPOINTS, 1184, 10);
	}

	/**
	 * Send all the skills to the client
	 */
	public void refresh() {
		for (Skill skill : skills) {
			player.getConnection().write(skill);
		}
	}

	/**
	 * Gets the total level.
	 * @return The total level.
	 */
	public int getTotalLevel() {
		int total = 0;
		for(int i = 0; i < skills.length; i++) {
			total += getLevelForExperience(i);
		}
		return total;
	}
	
	/**
	 * Sets a skill.
	 * @param skill The skill id.
	 * @param level The level.
	 * @param exp The experience.
	 */
	public void setSkill(int skill, int level, int exp) {
		player.getConnection().write(skills[skill].set(level, exp));
	}
	
	/**
	 * Sets a level.
	 * @param skill The skill id.
	 * @param level The level.
	 */
	public void setLevel(int skill, int level) {
		player.getConnection().write(skills[skill].setLevel(level));
	}
	
	/**
	 * Increments a level.
	 * @param skill The skill to increment.
	 */
	public void incrementLevel(int skill) {
		setLevel(skill, skills[skill].getLevel() + 1);
	}
	
	/**
	 * Normalizes a level (adjusts it until it is at its normal value).
	 * @param skill The skill to normalize.
	 */
	public void normalizeLevel(int skill) {
		int norm = getLevelForExperience(skill);
		if(skills[skill].getLevel() > norm) {
			decrementLevel(skill);
		} else if(skills[skill].getLevel() < norm) {
			incrementLevel(skill);
		}
	}
	
	/**
	 * Decrements a level.
	 * @param skill The skill to decrement.
	 */
	public void decrementLevel(int skill) {
		setLevel(skill, skills[skill].getLevel() - 1);
	}
	
	/**
	 * Sets experience.
	 * @param skill The skill id.
	 * @param exp The experience.
	 */
	public void setExperience(int skill, int exp) {
		int oldLvl = getLevelForExperience(skill);
		player.getConnection().write(skills[skill].setExperience(exp));
		int newLvl = getLevelForExperience(skill);
		if(oldLvl != newLvl) {
			player.getUpdateFlags().flag(UpdateFlag.APPEARANCE);
		}
	}
	
	/**
	 * Gets a level.
	 * @param skill The skill id.
	 * @return The level.
	 */
	public int getLevel(int skill) {
		return skills[skill].getLevel();
	}
	
	/**
	 * Gets a level by experience.
	 * @param skill The skill id.
	 * @return The level.
	 */
	public int getLevelForExperience(int skill) {
		double exp = skills[skill].getExperience();
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp)
				return lvl;
		}
		return 99;
	}
	
	/**
	 * Gets experience.
	 * @param skill The skill id.
	 * @return The experience.
	 */
	public double getExperience(int skill) {
		return skills[skill].getExperience();
	}
	
	/**
	 * Adds experience.
	 * @param skill The skill.
	 * @param exp The experience to add.
	 */
	public void addExperience(int skill, int exp) {
		int oldLevel = skills[skill].getLevel();
		skills[skill].setExperience(skills[skill].getExperience() + exp);
		if(skills[skill].getExperience() > MAXIMUM_EXP) {
			skills[skill].setExperience(MAXIMUM_EXP);
		}
		int newLevel = getLevelForExperience(skill);
		int levelDiff = newLevel - oldLevel;
		if(levelDiff > 0) {
			skills[skill].setLevel(skills[skill].getLevel() + levelDiff);
			player.getUpdateFlags().flag(UpdateFlag.APPEARANCE);
		}
		player.getConnection().write(skills[skill]);
	}

}