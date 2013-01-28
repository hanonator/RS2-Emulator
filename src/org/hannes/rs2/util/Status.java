package org.hannes.rs2.util;

/**
 * The public statuses of the player
 * 
 * @author red
 */
public class Status {
	
	/**
	 * Available to everyone, even people not on the friends list
	 */
	public static final int PUBLIC = 0;
	
	/**
	 * Available to anyone who is on the player's friends list
	 */
	public static final int PRIVATE = 1;
	
	/**
	 * Available to no-one, not even players on the friends list
	 */
	public static final int OFF = 2;
	
	/**
	 * Where the player can talk in the public chat, but all the chat
	 * text in the chatbox is hidden
	 */
	public static final int HIDDEN = 3;

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

	public Status() {
		this.publicChat = PUBLIC;
		this.privateChat = PUBLIC;
		this.trade = PUBLIC;
	}

	/**
	 * @return the publicChat
	 */
	public int getPublicChat() {
		return publicChat;
	}

	/**
	 * @param publicChat the publicChat to set
	 */
	public void setPublicChat(int publicChat) {
		this.publicChat = publicChat;
	}

	/**
	 * @return the privateChat
	 */
	public int getPrivateChat() {
		return privateChat;
	}

	/**
	 * @param privateChat the privateChat to set
	 */
	public void setPrivateChat(int privateChat) {
		this.privateChat = privateChat;
	}

	/**
	 * @return the trade
	 */
	public int getTrade() {
		return trade;
	}

	/**
	 * @param trade the trade to set
	 */
	public void setTrade(int trade) {
		this.trade = trade;
	}

}