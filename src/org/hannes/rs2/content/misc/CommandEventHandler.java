package org.hannes.rs2.content.misc;

import java.util.logging.Logger;

import org.hannes.entity.Player;
import org.hannes.event.CommandEvent;
import org.hannes.event.hub.EventHandler;
import org.hannes.locale.Location;
import org.hannes.rs2.container.Item;
import org.hannes.rs2.util.SidebarInterface;

/**
 * Handles the commands
 * 
 * @author red
 *
 */
public class CommandEventHandler implements EventHandler<CommandEvent> {

	/**
	 * The logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CommandEventHandler.class.getName());

	@Override
	public void handleEvent(CommandEvent event, Player player) throws Exception {
		try {
			final String[] args = event.getCommandText().split(" ");
			switch (args[0].toLowerCase()) {
			case "exp":
			case "xp":
				player.getSkills().addExperience(Integer.valueOf(args[1]), Integer.valueOf(args[2]));
				break;
	
			case "t":
			case "tele":
			case "teleport":
				player.setTeleportTarget(new Location(Integer.valueOf(args[1]), Integer.valueOf(args[2])));
				break;
				
			case "item":
				int amount = 1;
				if (args.length == 3) {
					amount = Integer.valueOf(args[2]);
				}
				player.getInventory().add(new Item(Integer.valueOf(args[1]), amount));
				break;
				
			case "bank":
				player.getBank().open();
				break;
				
			case "sidebar":
			case "sb":
				int index = Integer.valueOf(args[1]);
				int interfaceId = Integer.valueOf(args[2]);
				
				player.getConnection().write(new SidebarInterface(index, interfaceId));
				break;
				
			default:
				logger.info(player.getUsername() + " used command \"" + event.getCommandText() + "\"");
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}