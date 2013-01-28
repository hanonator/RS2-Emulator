package org.hannes.rs2.content;

import org.hannes.Main;
import org.hannes.entity.Player;
import org.hannes.event.InitializeEvent;
import org.hannes.event.hub.EventHandler;
import org.hannes.rs2.util.TextMessage;
import org.hannes.sql.impl.BankLoadQuery;
import org.hannes.sql.impl.EquipmentLoadQuery;
import org.hannes.sql.impl.InventoryLoadQuery;
import org.hannes.sql.impl.LoadSettingsQuery;
import org.hannes.sql.impl.LoadSidebarQuery;

/**
 * 
 * 
 * @author red
 *
 */
public class InitializeEventHandler implements EventHandler<InitializeEvent> {

	@Override
	public void handleEvent(InitializeEvent event, Player player) throws Exception {
		/*
		 * Send the welcoming message
		 */
		player.getConnection().write(new TextMessage("Welcome to " + Main.getConfiguration().get("server-name")));
		
		/*
		 * Refresh the player's skills
		 */
		player.getSkills().refresh();
		
		/*
		 * Reset the player's equipment
		 */
		player.getEquipment().reset();
		
		/*
		 * reset the player's inventory
		 */
		player.getInventory().reset();
		
		/*
		 * Reset the player's bank
		 */
		player.getBank().refresh();
		
		/*
		 * Load the player's settings
		 */
		Main.getCharacterdatabase().submit(new LoadSettingsQuery(player));
		
		/*
		 * Load the player's sidebar
		 */
		Main.getCharacterdatabase().submit(new LoadSidebarQuery(player));
		
		/*
		 * Load the player's sidebar
		 */
		Main.getCharacterdatabase().submit(new EquipmentLoadQuery(player));
		
		/*
		 * Load the player's sidebar
		 */
		Main.getCharacterdatabase().submit(new InventoryLoadQuery(player));
		
		/*
		 * Load the player's sidebar
		 */
		Main.getCharacterdatabase().submit(new BankLoadQuery(player));
	}

}