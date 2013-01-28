package org.hannes.rs2.content.misc;

import org.hannes.entity.Player;
import org.hannes.event.MoveItemEvent;
import org.hannes.event.hub.EventHandler;
import org.hannes.rs2.container.impl.Bank;
import org.hannes.rs2.container.impl.Inventory;

public class MoveItemEventHandler implements EventHandler<MoveItemEvent> {

	@Override
	public void handleEvent(MoveItemEvent event, Player player) throws Exception {
		switch (event.getInterfaceId()) {
		case Bank.INTERFACE_ID:
			if (player.getBank().swap()) {
				player.getBank().swap(event.getSource(), event.getDestination());
			} else {
				player.getBank().insert(event.getSource(), event.getDestination());
			}
			break;
		case Inventory.INTERFACE_ID:
			player.getInventory().swap(event.getSource(), event.getDestination());
			break;
		}
	}

}