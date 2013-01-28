package org.hannes.event;

import org.hannes.net.Connection;

public class IdleEvent extends Event {

	public IdleEvent(Connection connection) {
		super(connection);
	}

}