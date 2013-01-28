package org.hannes.rs2.content.misc;

import org.hannes.entity.Player;
import org.hannes.event.InterfaceItemEvent;
import org.hannes.event.hub.EventHandler;
import org.hannes.rs2.container.Item;
import org.hannes.rs2.container.impl.Bank;

public class WithdrawInterfaceEventHandler implements EventHandler<InterfaceItemEvent> {

	/**
	 * lol
	 */
	private static final int[] AMOUNT = {1, 5, 10};

	@Override
	public void handleEvent(InterfaceItemEvent event, Player player) throws Exception {
		if (event.getInterfaceId() == Bank.INTERFACE_ID) {
			Item item = player.getBank().get(event.getSlot());
			
			/*
			 * Anti-h4x
			 */
			if (item == null || item.getId() != event.getItemId()) {
				return;
			}
			
			/*
			 * Deposit
			 */
			switch (event.getIndex()) {
			case 1:
			case 2:
			case 3:
				player.getBank().withdraw(event.getSlot(), AMOUNT[event.getIndex() - 1]);
				break;
			case 4:
				player.getBank().withdraw(event.getSlot(), player.getInventory().count(event.getItemId()));
				break;
			case 5:
				// TODO
				break;
			}
		}
	}

}