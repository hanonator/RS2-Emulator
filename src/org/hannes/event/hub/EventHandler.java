package org.hannes.event.hub;

import org.hannes.entity.Player;
import org.hannes.event.Event;

/**
 * 
 * @author red
 *
 * @param <T>
 */
public interface EventHandler<T extends Event> {

	/**
	 * 
	 * @param event
	 * @param player
	 * @throws Exception
	 */
	public abstract void handleEvent(T event, Player player) throws Exception;

}