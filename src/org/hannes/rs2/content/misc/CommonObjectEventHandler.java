package org.hannes.rs2.content.misc;

import org.hannes.entity.Player;
import org.hannes.event.ObjectInteractionEvent;
import org.hannes.event.hub.EventHandler;

/**
 * Event handler for ladders, bookcases, drawers, crates, etc.
 * 
 * @author red
 *
 */
public class CommonObjectEventHandler implements EventHandler<ObjectInteractionEvent> {

	@Override
	public void handleEvent(ObjectInteractionEvent event, Player player) throws Exception {
		System.out.println(event.getId() + "," + event.getX() + "," + event.getY() + "," + event.getAction());
	}

}