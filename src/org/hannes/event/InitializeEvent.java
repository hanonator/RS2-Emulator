package org.hannes.event;

import org.hannes.net.Connection;

public class InitializeEvent extends Event {

	public InitializeEvent(Connection connection) {
		super(connection);
	}

}