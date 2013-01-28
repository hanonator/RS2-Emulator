package org.hannes.rs2.content.misc;

import org.hannes.entity.Player;
import org.hannes.event.InterfaceItemEvent;
import org.hannes.event.hub.EventHandler;
import org.hannes.rs2.container.Item;
import org.hannes.rs2.container.impl.Bank;
import org.hannes.rs2.container.impl.Equipment;

public class DepositInterfaceEventHandler implements EventHandler<InterfaceItemEvent> {

	private static final int[] AMOUNT = {1, 5, 10};

	@Override
	public void handleEvent(InterfaceItemEvent event, Player player) throws Exception {
		if (event.getInterfaceId() == Equipment.INTERFACE) {
			Item item = player.getEquipment().get(event.getSlot());
			
			/*
			 * Anti-h4x
			 */
			if (item == null || item.getId() != event.getItemId()) {
				return;
			}
			
			/*
			 * Remove teh itemz
			 */
			player.getEquipment().unequip(event.getSlot());
		} else if (event.getInterfaceId() == Bank.INTERFACE_ID_INVENTORY) {
			Item item = player.getInventory().get(event.getSlot());
			
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
				player.getBank().deposit(event.getSlot(), AMOUNT[event.getIndex() - 1]);
				break;
			case 4:
				player.getBank().deposit(event.getSlot(), player.getInventory().count(event.getItemId()));
				break;
			case 5:
				// TODO
				break;
			}
		}
	}

}