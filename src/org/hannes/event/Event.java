package org.hannes.event;

import org.hannes.net.Connection;

/**
 * 
 * @author red
 *
 */
public abstract class Event {

	/**
	 * 
	 */
	private final Connection connection;

	/**
	 * 
	 * @param connection
	 */
	public Event(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

}