package org.hannes.event;

import org.hannes.net.Connection;

public class StatusUpdateEvent extends Event {

	/**
	 * The public chat status
	 */
	private int publicChat;
	
	/**
	 * The private chat status
	 */
	private int privateChat;
	
	/**
	 * The trade status
	 */
	private int trade;

	public StatusUpdateEvent(Connection connection) {
		super(connection);
	}

	/**
	 * @return the publicChat
	 */
	public int getPublicChat() {
		return publicChat;
	}

	/**
	 * @return the privateChat
	 */
	public int getPrivateChat() {
		return privateChat;
	}

	/**
	 * @return the trade
	 */
	public int getTrade() {
		return trade;
	}

}