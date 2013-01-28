package org.hannes.rs2.content.misc;

import org.hannes.entity.Player;
import org.hannes.event.IdleEvent;
import org.hannes.event.hub.EventHandler;
import org.hannes.rs2.util.LogoutRequest;

public class IdleEventHandler implements EventHandler<IdleEvent> {

	@Override
	public void handleEvent(IdleEvent event, Player player) throws Exception {
		player.getConnection().write(new LogoutRequest());
	}

}