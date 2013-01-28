package org.hannes.event;

import org.hannes.net.Connection;

/**
 * 
 * @author red
 *
 */
public class CommandEvent extends Event {

	/**
	 * The entered command text
	 */
	private String commandText;

	public CommandEvent(Connection connection) {
		super(connection);
	}

	public String getCommandText() {
		return commandText;
	}

}