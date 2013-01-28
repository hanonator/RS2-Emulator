package org.hannes.rs2.content.misc;

import org.hannes.entity.Player;
import org.hannes.event.StatusUpdateEvent;
import org.hannes.event.hub.EventHandler;

public class StatusUpdateEventHandler implements EventHandler<StatusUpdateEvent> {

	@Override
	public void handleEvent(StatusUpdateEvent event, Player player) throws Exception {
		player.getStatus().setPrivateChat(event.getPrivateChat());
		player.getStatus().setPublicChat(event.getPublicChat());
		player.getStatus().setTrade(event.getTrade());
	}

}