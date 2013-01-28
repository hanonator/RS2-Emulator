package org.hannes.rs2.content.misc;

import java.util.logging.Logger;

import org.hannes.Main;
import org.hannes.entity.Player;
import org.hannes.event.ButtonEvent;
import org.hannes.event.hub.EventHandler;
import org.hannes.rs2.util.LogoutRequest;

/**
 * Handles the buttons for common requests such as logout, change of the
 * volume settings, etc.
 * 
 * Also debugs default 
 * 
 * @author red
 *
 */
public class CommonButtonEventHandler implements EventHandler<ButtonEvent> {

	/**
	 * The logger for debug information
	 */
	private static final Logger logger = Logger.getLogger(CommonButtonEventHandler.class.getName());

	/**
	 * The logout button
	 */
	public static final int LOGOUT_BUTTON = 2458;
	
	/**
	 * The button id of the button that enables the swapping of items
	 */
	public static final int BANK_SWAP_BUTTON = 8130;

	/**
	 * The button id of the button that enables the inserting of items (not in anus)
	 */
	public static final int BANK_INSERT_BUTTON = 8131;
	
	/**
	 * The button id of the button that enables the swapping of items
	 */
	public static final int BANK_NOTE_BUTTON = 5386;

	/**
	 * The button id of the button that enables the inserting of items (not in anus)
	 */
	public static final int BANK_ITEM_BUTTON = 5387;

	@Override
	public void handleEvent(ButtonEvent event, Player player) throws Exception {
		/*
		 * Debug if necessary
		 */
		if (Main.getConfiguration().contains("debug-mode") && 
				Main.getConfiguration().get("debug-mode").equals("true")) {
			logger.info("button-press: " + event.getIndex());
		}
		
		/*
		 * Handle dem butns
		 */
		switch (event.getIndex()) {
		case LOGOUT_BUTTON:
			player.getConnection().write(new LogoutRequest());
			break;
			
		case BANK_INSERT_BUTTON:
		case BANK_SWAP_BUTTON:
			player.getBank().setSwap(event.getIndex() == BANK_SWAP_BUTTON);
			break;
			
		case BANK_NOTE_BUTTON:
		case BANK_ITEM_BUTTON:
			player.getBank().setNotes(event.getIndex() == BANK_NOTE_BUTTON);
			break;
		}
	}

}