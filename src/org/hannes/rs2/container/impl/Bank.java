package org.hannes.rs2.container.impl;

import org.hannes.Main;
import org.hannes.entity.Player;
import org.hannes.observer.Observable;
import org.hannes.observer.Observer;
import org.hannes.rs2.container.Container;
import org.hannes.rs2.container.ContainerContext;
import org.hannes.rs2.container.ContainerEvent;
import org.hannes.rs2.container.Item;
import org.hannes.rs2.container.StackingPolicy;
import org.hannes.rs2.util.DoubleInterface;
import org.hannes.sql.impl.BankUpdateQuery;

public class Bank extends Container implements Observer<ContainerEvent> {

	/**
	 * The size of the bank (352 is 317)
	 */
	public static final int SIZE = 252;
	
	/**
	 * The id of the interface ID for the items in the bank
	 */
	public static final int INTERFACE_ID = 5382;

	/**
	 * The interface id for the inventory
	 */
	public static final int INTERFACE_ID_INVENTORY = 5064;
	
	/**
	 * The player
	 */
	private final Player player;

	/**
	 * If true, withdraw items as notes, otherwise just items (durr)
	 */
	private boolean notes;
	
	/**
	 * If true, use <code>swap</code> otherwise use <code>insert</code>
	 */
	private boolean swap;

	public Bank(Player player) {
		super (SIZE, StackingPolicy.ALWAYS);
		
		this.player = player;
		this.register(this);
	}

	@Override
	public synchronized void set(int slot, Item item) {
		super.set(slot, item);
		Main.getCharacterdatabase().submit(new BankUpdateQuery(player, super.toArray(), new int[] {slot}));
	}
	
	@Override
	public void shift() {
		super.shift();
		Main.getCharacterdatabase().submit(new BankUpdateQuery(player, super.toArray(), null));
	}

	@Override
	public void update(Observable<ContainerEvent> observable, ContainerEvent object) throws Exception {
		player.getConnection().write(new ContainerContext(this, INTERFACE_ID));
	}

	@Override
	public void exceptionCaught(Observable<ContainerEvent> observable, Throwable exception) {
		exception.printStackTrace();
	}

	/**
	 * Withdraw an item from a given slot (bank slot)
	 * 
	 * @param slot
	 * @return amount of items left to add
	 */
	public boolean withdraw(int slot, int amount) {
		/*
		 * See if the slot is valid, the item is existant and the amount is positive
		 */
		if (slot >= 0 && slot < super.size() && super.get(slot) != null && amount > 0) {
			final Item item = super.get(slot);
			
			/*
			 * Whether or not the item stacks. This is necessary, yo
			 */
			boolean stacks = item.getDefinition().isStackable() || (notes && item.getDefinition().isNoteable());
			
			/*
			 * Calculate the space need to withdraw the item, if not, try to withdraw the item again but with the amount of space available
			 */
			int spaceNeeded = stacks ? 1 : amount;
			if (spaceNeeded > player.getInventory().available() && !(stacks && player.getInventory().contains(item.getId()))) {
				return withdraw(slot, player.getInventory().available());
			}
			
			/*
			 * Amount - leftovers of the delete
			 */
			int count = amount - super.remove(item.getId(), amount, slot);
			
			/*
			 * Add the items (as many as deleted above) and return
			 */
			return player.getInventory().add(notes && item.getDefinition().isNoteable() ? item.getDefinition().getNotedId() : item.getId(), count);
		}
		return false;
	}

	/**
	 * Deposit an item from a given slot (inventory slot)
	 * 
	 * @param slot
	 * @return amount of items left to deposit
	 */
	public boolean deposit(int slot, int amount) {
		/*
		 * See if the slot is valid, the item is existent and the amount is positive
		 */
		if (slot >= 0 && slot < player.getInventory().size() && player.getInventory().get(slot) != null && amount > 0 && super.available() >= 0) {
			final Item item = player.getInventory().get(slot);
			
			/*
			 * To prevent weird things
			 */
			int count = player.getInventory().count(item.getId());
			if (count < amount) {
				amount = count;
			}
			
			/*
			 * Amount - leftovers of the delete
			 */
			player.getInventory().remove(item.getId(), amount);
			
			/*
			 * Add the items (as many as deleted above) and return
			 */
			return super.add(item.getDefinition().isNoted() ? item.getDefinition().getNormalId() : item.getId(), amount);
		}
		return false;
	}


	/**
	 * Opens the bank, refreshes the interface and shifts all the items
	 */
	public void open() {
		shift();
		refresh();
		
		player.getConnection().write(new DoubleInterface(5292, 5063));
	}

	public void setSwap(boolean swap) {
		this.swap = swap;
	}

	public void setNotes(boolean notes) {
		this.notes = notes;
	}

	public boolean swap() {
		return swap;
	}

	public void load(int slot, Item item) {
		super.set(slot, item);
	}

}