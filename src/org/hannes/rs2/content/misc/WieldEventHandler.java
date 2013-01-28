package org.hannes.rs2.content.misc;

import org.hannes.entity.Player;
import org.hannes.event.EquipEvent;
import org.hannes.event.hub.EventHandler;

public class WieldEventHandler implements EventHandler<EquipEvent> {

	@Override
	public void handleEvent(EquipEvent event, Player player) throws Exception {
		player.getEquipment().equip(event);
	}

}