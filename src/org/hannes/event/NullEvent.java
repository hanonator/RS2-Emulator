package org.hannes.event;

import org.hannes.net.Connection;

/**
 * Does nothing, this is here so packets don't spam the console
 * with "unknown message"
 * 
 * @author red
 *
 */
public class NullEvent extends Event {

	public NullEvent(Connection connection) {
		super(connection);
	}

}