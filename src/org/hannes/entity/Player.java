package org.hannes.entity;

import org.hannes.net.Connection;
import org.hannes.rs2.Skills;
import org.hannes.rs2.container.impl.Bank;
import org.hannes.rs2.container.impl.Equipment;
import org.hannes.rs2.container.impl.Inventory;
import org.hannes.rs2.content.misc.Animations;
import org.hannes.rs2.util.MemoryUsage;
import org.hannes.rs2.util.Settings;
import org.hannes.rs2.util.Sidebar;
import org.hannes.rs2.util.Status;

/**
 * Represents a player
 * 
 * @author red
 *
 */
public class Player extends Character {

	/**
	 * This player's connection
	 */
	private final Connection connection;
	
	/**
	 * The player's username
	 */
	private String username;
	
	/**
	 * The clien type
	 */
	private MemoryUsage clientType;
	
	/**
	 * The player's skills
	 */
	private final Skills skills = new Skills(this);
	
	/**
	 * The player's inventory
	 */
	private final Inventory inventory = new Inventory(this);
	
	/**
	 * The player's status
	 */
	private final Status status = new Status();
	
	/**
	 * The player's settings
	 */
	private final Settings settings = new Settings();
	
	/**
	 * The player's Equipment
	 */
	private final Equipment equipment = new Equipment(this);
	
	/**
	 * The player's side bar
	 */
	private final Sidebar sidebar = new Sidebar();
	
	/**
	 * The bank
	 */
	private final Bank bank = new Bank(this);
	
	/**
	 * The player's uid
	 */
	private int uid;
	
	/**
	 * The player's animations
	 */
	private Animations animations;
	
	/**
	 * The attack index
	 */
	private int attackIndex;

	/**
	 * Create a new player on a given index for a given connection
	 * 
	 * @param index
	 * @param connection
	 */
	public Player(int index, Connection connection) {
		super(index);
		 
		this.connection = connection;
	}

	@Override
	public int getAcquaintanceIndex() {
		return super.getIndex() + 32768;
	}

	public Connection getConnection() {
		return connection;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public MemoryUsage getMemoryVersion() {
		return clientType;
	}

	public void setClientType(MemoryUsage memoryVersion) {
		this.clientType = memoryVersion;
	}

	public Skills getSkills() {
		return skills;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getUid() {
		return uid;
	}

	public Status getStatus() {
		return status;
	}

	public Settings getSettings() {
		return settings;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public Sidebar getSidebar() {
		return sidebar;
	}

	public Bank getBank() {
		return bank;
	}

	public Animations getAnimations() {
		return animations;
	}

	public void setAnimations(Animations animations) {
		this.animations = animations;
	}

	public int getAttackIndex() {
		return attackIndex;
	}

	public void setAttackIndex(int attackIndex) {
		this.attackIndex = attackIndex;
	}

}